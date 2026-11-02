package com.example.lab4kalkulkator.model

val basicButtons = listOf(
    // Row 1
    CalculatorButton(label = "C", textToInsert = "C", type = ButtonType.ACTION),
    CalculatorButton(label = "(", textToInsert = "(", type = ButtonType.OPERATOR),
    CalculatorButton(label = ")", textToInsert = ")", type = ButtonType.OPERATOR),
    CalculatorButton(label = "÷", textToInsert = "/", type = ButtonType.OPERATOR),

    // Row 2
    CalculatorButton(label = "7", textToInsert = "7", type = ButtonType.DIGIT),
    CalculatorButton(label = "8", textToInsert = "8", type = ButtonType.DIGIT),
    CalculatorButton(label = "9", textToInsert = "9", type = ButtonType.DIGIT),
    CalculatorButton(label = "×", textToInsert = "*", type = ButtonType.OPERATOR),

    // Row 3
    CalculatorButton(label = "4", textToInsert = "4", type = ButtonType.DIGIT),
    CalculatorButton(label = "5", textToInsert = "5", type = ButtonType.DIGIT),
    CalculatorButton(label = "6", textToInsert = "6", type = ButtonType.DIGIT),
    CalculatorButton(label = "−", textToInsert = "-", type = ButtonType.OPERATOR),

    // Row 4
    CalculatorButton(label = "1", textToInsert = "1", type = ButtonType.DIGIT),
    CalculatorButton(label = "2", textToInsert = "2", type = ButtonType.DIGIT),
    CalculatorButton(label = "3", textToInsert = "3", type = ButtonType.DIGIT),
    CalculatorButton(label = "+", textToInsert = "+", type = ButtonType.OPERATOR),

    // Row 5
    CalculatorButton(label = "0", textToInsert = "0", type = ButtonType.DIGIT),
    CalculatorButton(label = ".", textToInsert = ".", type = ButtonType.DIGIT),
    CalculatorButton(label = "⌫", textToInsert = "DEL", type = ButtonType.ACTION),
    CalculatorButton(label = "=", textToInsert = "=", type = ButtonType.EQUALS)
)

val scientificButtons = listOf(
    // Row 1
    CalculatorButton(label = "sin", textToInsert = "sin(", type = ButtonType.SCIENTIFIC, isScientific = true),
    CalculatorButton(label = "cos", textToInsert = "cos(", type = ButtonType.SCIENTIFIC, isScientific = true),

    // Row 2
    CalculatorButton(label = "tan", textToInsert = "tan(", type = ButtonType.SCIENTIFIC, isScientific = true),
    CalculatorButton(label = "√", textToInsert = "√(", type = ButtonType.SCIENTIFIC, isScientific = true),

    // Row 3
    CalculatorButton(label = "ln", textToInsert = "ln(", type = ButtonType.SCIENTIFIC, isScientific = true),
    CalculatorButton(label = "log", textToInsert = "log(", type = ButtonType.SCIENTIFIC, isScientific = true),

    // Row 4
    CalculatorButton(label = "x²", textToInsert = "^2", type = ButtonType.SCIENTIFIC, isScientific = true),
    CalculatorButton(label = "xʸ", textToInsert = "^", type = ButtonType.SCIENTIFIC, isScientific = true),

    // Row 5
    CalculatorButton(label = "π", textToInsert = "π", type = ButtonType.SCIENTIFIC, isScientific = true),
    CalculatorButton(label = "e", textToInsert = "e", type = ButtonType.SCIENTIFIC, isScientific = true)
)
