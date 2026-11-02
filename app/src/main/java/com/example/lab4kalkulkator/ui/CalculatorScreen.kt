package com.example.lab4kalkulkator.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab4kalkulkator.model.CalculatorButton
import com.example.lab4kalkulkator.model.basicButtons
import com.example.lab4kalkulkator.model.scientificButtons
import com.example.lab4kalkulkator.ui.theme.Lab4kalkulkatorTheme

@Composable
fun CalculatorScreen(
    modifier: Modifier = Modifier,
) {
    var expression by remember { mutableStateOf("sin(π/2) + 12.5 × 4") }
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val onButtonClick: (CalculatorButton) -> Unit = { button ->
        when (button.textToInsert) {
            "C" -> expression = ""
            "DEL" -> if (expression.length > 0) expression = expression.subSequence(0, expression.length - 1).toString()
            else -> expression += button.textToInsert
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Expression Display at the top
            CalculatorDisplay(
                expression = expression,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Buttons Area - adapting to Orientation
            if (isLandscape) {
                // Landscape: Scientific Block (Left) + Basic Block (Right)
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CalculatorButtonGrid(
                        buttons = scientificButtons,
                        columnsCount = 2,
                        onButtonClick = onButtonClick,
                        modifier = Modifier.weight(2f)
                    )
                    CalculatorButtonGrid(
                        buttons = basicButtons,
                        columnsCount = 4,
                        onButtonClick = onButtonClick,
                        modifier = Modifier.weight(4f)
                    )
                }
            } else {
                // Portrait: Basic Block only
                CalculatorButtonGrid(
                    buttons = basicButtons,
                    columnsCount = 4,
                    onButtonClick = onButtonClick,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Portrait")
@Composable
fun CalculatorScreenPortraitPreview() {
    Lab4kalkulkatorTheme {
        CalculatorScreen()
    }
}

@Preview(
    showBackground = true,
    name = "Landscape",
    widthDp = 800,
    heightDp = 360
)
@Composable
fun CalculatorScreenLandscapePreview() {
    Lab4kalkulkatorTheme {
        CalculatorScreen()
    }
}

@Preview(showBackground = true, name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CalculatorScreenDarkPreview() {
    Lab4kalkulkatorTheme(darkTheme = true) {
        CalculatorScreen()
    }
}
