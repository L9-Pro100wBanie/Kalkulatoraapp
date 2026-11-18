package com.example.lab4kalkulator

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.License

/**
 * ViewModel odpowiadający za zarządzanie stanem i logiką kalkulatora.
 * Używa [SavedStateHandle] do automatycznego zachowania stanu (np. przy obrocie ekranu).
 * Obliczenia wykonywane są za pomocą zewnętrznej biblioteki mXparser.
 */
class CalculatorViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val KEY_EXPRESSION = "key_expression"
        private const val KEY_RESULT = "key_result"
        private const val KEY_IS_ERROR = "key_is_error"
        private const val KEY_IS_CALCULATED = "key_is_calculated"
    }

    init {
        // Potwierdzenie niekomercyjnego użycia biblioteki mXparser v5+ / v6+
        License.iConfirmNonCommercialUse("Lab5_Calculator")
    }

    // Stan wyrażenia wprowadzonego przez użytkownika
    val expression: StateFlow<String> = savedStateHandle.getStateFlow(KEY_EXPRESSION, "")

    // Stan wyniku obliczeń
    val result: StateFlow<String> = savedStateHandle.getStateFlow(KEY_RESULT, "")

    // Flaga określająca, czy wystąpił błąd obliczeń/składni
    val isError: StateFlow<Boolean> = savedStateHandle.getStateFlow(KEY_IS_ERROR, false)

    // Flaga określająca, czy aktualny wynik pochodzi z ukończonego obliczenia
    private val isCalculated: StateFlow<Boolean> = savedStateHandle.getStateFlow(KEY_IS_CALCULATED, false)

    private val isOperatorSet = setOf("+", "-", "*", "/", "^", "%")

    /**
     * Dopisuje podany znak lub symbol do bieżącego wyrażenia.
     */
    fun onCharacterInput(input: String) {
        if (isError.value) {
            onClear()
        }

        val currentResult = result.value
        val currentExpr = expression.value

        if (isCalculated.value) {
            savedStateHandle[KEY_IS_CALCULATED] = false
            if (input in isOperatorSet && currentResult.isNotEmpty()) {
                // Łańcuchowe kontynuowanie obliczeń: "wynik + nowy_operator"
                savedStateHandle[KEY_EXPRESSION] = currentResult + input
                return
            } else {
                // Nowe obliczenie od zera
                savedStateHandle[KEY_EXPRESSION] = input
                savedStateHandle[KEY_RESULT] = ""
                return
            }
        }

        savedStateHandle[KEY_EXPRESSION] = currentExpr + input
    }

    /**
     * Wstawia funkcję naukową z nawiasem otwierającym, np. "sin(", "sqrt(".
     */
    fun onFunctionInput(funcName: String) {
        if (isError.value) {
            onClear()
        }

        if (isCalculated.value) {
            savedStateHandle[KEY_IS_CALCULATED] = false
            savedStateHandle[KEY_EXPRESSION] = "$funcName("
            savedStateHandle[KEY_RESULT] = ""
            return
        }

        val current = expression.value
        savedStateHandle[KEY_EXPRESSION] = "$current$funcName("
    }

    /**
     * Czyszczenie całego ekranu kalkulatora (przycisk AC / C).
     */
    fun onClear() {
        savedStateHandle[KEY_EXPRESSION] = ""
        savedStateHandle[KEY_RESULT] = ""
        savedStateHandle[KEY_IS_ERROR] = false
        savedStateHandle[KEY_IS_CALCULATED] = false
    }

    /**
     * Usunięcie ostatniego znaku (przycisk DEL / Backspace).
     */
    fun onDelete() {
        if (isError.value || isCalculated.value) {
            onClear()
            return
        }
        val current = expression.value
        if (current.isNotEmpty()) {
            savedStateHandle[KEY_EXPRESSION] = current.substring(0, current.length - 1)
        }
    }

    /**
     * Obliczenie wartości wyrażenia za pomocą biblioteki mXparser.
     */
    fun onCalculate() {
        val currentExpression = expression.value.trim()
        if (currentExpression.isEmpty()) return

        try {
            val expr = Expression(currentExpression)

            // Sprawdzenie poprawności składniowej
            if (!expr.checkSyntax()) {
                savedStateHandle[KEY_IS_ERROR] = true
                savedStateHandle[KEY_RESULT] = "Błąd składni"
                savedStateHandle[KEY_IS_CALCULATED] = false
                return
            }

            val calcResult = expr.calculate()

            if (calcResult.isNaN()) {
                savedStateHandle[KEY_IS_ERROR] = true
                savedStateHandle[KEY_RESULT] = "Błąd (NaN)"
                savedStateHandle[KEY_IS_CALCULATED] = false
            } else {
                savedStateHandle[KEY_IS_ERROR] = false
                savedStateHandle[KEY_RESULT] = formatResult(calcResult)
                savedStateHandle[KEY_IS_CALCULATED] = true
            }
        } catch (e: Exception) {
            savedStateHandle[KEY_IS_ERROR] = true
            savedStateHandle[KEY_RESULT] = "Błąd"
            savedStateHandle[KEY_IS_CALCULATED] = false
        }
    }

    /**
     * Pomocnicza funkcja formatująca wynik (usuwa niepotrzebne końcowe .0 dla liczb całkowitych).
     */
    private fun formatResult(value: Double): String {
        return if (value % 1.0 == 0.0 && value >= Long.MIN_VALUE.toDouble() && value <= Long.MAX_VALUE.toDouble()) {
            value.toLong().toString()
        } else {
            value.toString()
        }
    }
}
