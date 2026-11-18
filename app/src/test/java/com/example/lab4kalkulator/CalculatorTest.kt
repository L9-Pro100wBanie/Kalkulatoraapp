package com.example.lab4kalkulator

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.License

/**
 * Testy jednostkowe (Local Unit Tests) weryfikujące poprawność obliczeń
 * kalkulatora oraz działanie logiki w ViewModelu.
 */
class CalculatorTest {

    @Before
    fun setUp() {
        License.iConfirmNonCommercialUse("Lab5_Test")
    }

    @Test
    fun testBasicArithmetic() {
        val exprAdd = Expression("12 + 8")
        assertEquals(20.0, exprAdd.calculate(), 1e-9)

        val exprSub = Expression("50 - 15")
        assertEquals(35.0, exprSub.calculate(), 1e-9)

        val exprMul = Expression("6 * 7")
        assertEquals(42.0, exprMul.calculate(), 1e-9)

        val exprDiv = Expression("20 / 4")
        assertEquals(5.0, exprDiv.calculate(), 1e-9)
    }

    @Test
    fun testOrderOfOperations() {
        // Kolejność działań: mnożenie przed dodawaniem
        val expr = Expression("2 + 2 * 2")
        assertEquals(6.0, expr.calculate(), 1e-9)
    }

    @Test
    fun testParentheses() {
        // Nawiasy zmieniają kolejność działań
        val expr = Expression("(2 + 2) * 2")
        assertEquals(8.0, expr.calculate(), 1e-9)
    }

    @Test
    fun testScientificFunctions() {
        val exprSqrt = Expression("sqrt(16)")
        assertEquals(4.0, exprSqrt.calculate(), 1e-9)

        val exprSin = Expression("sin(0)")
        assertEquals(0.0, exprSin.calculate(), 1e-9)

        val exprCos = Expression("cos(0)")
        assertEquals(1.0, exprCos.calculate(), 1e-9)

        val exprTan = Expression("tan(0)")
        assertEquals(0.0, exprTan.calculate(), 1e-9)

        val exprLog = Expression("log10(100)")
        assertEquals(2.0, exprLog.calculate(), 1e-9)

        val exprLn = Expression("ln(e)")
        assertEquals(1.0, exprLn.calculate(), 1e-9)

        val exprPow = Expression("2^3")
        assertEquals(8.0, exprPow.calculate(), 1e-9)

        val exprPi = Expression("pi")
        assertEquals(Math.PI, exprPi.calculate(), 1e-9)
    }

    @Test
    fun testDivisionByZero() {
        val expr = Expression("5 / 0")
        val result = expr.calculate()
        // Dzielenie przez 0 w mXparser zwraca Double.NaN
        assertTrue(result.isNaN())
    }

    @Test
    fun testInvalidSyntax() {
        val expr = Expression("2 + * 3")
        assertFalse(expr.checkSyntax())
    }

    @Test
    fun testViewModelInputAndCalculation() {
        val savedStateHandle = SavedStateHandle()
        val viewModel = CalculatorViewModel(savedStateHandle)

        // Symulacja wpisywania: 5 + 3 * 2
        viewModel.onCharacterInput("5")
        viewModel.onCharacterInput("+")
        viewModel.onCharacterInput("3")
        viewModel.onCharacterInput("*")
        viewModel.onCharacterInput("2")

        assertEquals("5+3*2", viewModel.expression.value)

        // Wykonanie obliczenia
        viewModel.onCalculate()

        assertFalse(viewModel.isError.value)
        assertEquals("11", viewModel.result.value)
    }

    @Test
    fun testSmartChainingResult() {
        val savedStateHandle = SavedStateHandle()
        val viewModel = CalculatorViewModel(savedStateHandle)

        // Wykonujemy: 5 + 3 = 8
        viewModel.onCharacterInput("5")
        viewModel.onCharacterInput("+")
        viewModel.onCharacterInput("3")
        viewModel.onCalculate()
        assertEquals("8", viewModel.result.value)

        // Naciśnięcie "+" kontynuuje obliczenie z wynikiem 8: "8+"
        viewModel.onCharacterInput("+")
        assertEquals("8+", viewModel.expression.value)

        viewModel.onCharacterInput("2")
        viewModel.onCalculate()
        assertEquals("10", viewModel.result.value)
    }

    @Test
    fun testNewCalculationAfterEquals() {
        val savedStateHandle = SavedStateHandle()
        val viewModel = CalculatorViewModel(savedStateHandle)

        // Wykonujemy: 5 + 3 = 8
        viewModel.onCharacterInput("5")
        viewModel.onCharacterInput("+")
        viewModel.onCharacterInput("3")
        viewModel.onCalculate()
        assertEquals("8", viewModel.result.value)

        // Naciśnięcie cyfry "7" rozpoczyna nowe działanie
        viewModel.onCharacterInput("7")
        assertEquals("7", viewModel.expression.value)
    }

    @Test
    fun testViewModelClearAndDelete() {
        val savedStateHandle = SavedStateHandle()
        val viewModel = CalculatorViewModel(savedStateHandle)

        viewModel.onCharacterInput("123")
        assertEquals("123", viewModel.expression.value)

        viewModel.onDelete()
        assertEquals("12", viewModel.expression.value)

        viewModel.onClear()
        assertEquals("", viewModel.expression.value)
        assertEquals("", viewModel.result.value)
        assertFalse(viewModel.isError.value)
    }
}
