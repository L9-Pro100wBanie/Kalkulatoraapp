package com.example.lab4kalkulkator.model

enum class ButtonType {
    DIGIT,
    OPERATOR,
    ACTION,
    EQUALS,
    SCIENTIFIC
}

data class CalculatorButton(
    val label: String,
    val textToInsert: String = label,
    val type: ButtonType,
    val isScientific: Boolean = false
)
