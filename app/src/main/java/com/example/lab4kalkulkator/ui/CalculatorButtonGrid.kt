package com.example.lab4kalkulkator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab4kalkulkator.model.ButtonType
import com.example.lab4kalkulkator.model.CalculatorButton

private data class ButtonColors(
    val container: Color,
    val content: Color
)

@Composable
fun CalculatorButtonGrid(
    buttons: List<CalculatorButton>,
    columnsCount: Int,
    onButtonClick: (CalculatorButton) -> Unit,
    modifier: Modifier = Modifier
) {
    // Chunk buttons into rows based on specified column count
    val rows = buttons.chunked(columnsCount)

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (row in rows) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (button in row) {
                    CalculatorButtonItem(
                        button = button,
                        onClick = { onButtonClick(button) },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )
                }
            }
        }
    }
}

@Composable
fun CalculatorButtonItem(
    button: CalculatorButton,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = when (button.type) {
        ButtonType.DIGIT -> ButtonColors(
            container = MaterialTheme.colorScheme.surfaceContainerHigh,
            content = MaterialTheme.colorScheme.onSurface
        )
        ButtonType.OPERATOR -> ButtonColors(
            container = MaterialTheme.colorScheme.secondaryContainer,
            content = MaterialTheme.colorScheme.onSecondaryContainer
        )
        ButtonType.ACTION -> ButtonColors(
            container = MaterialTheme.colorScheme.errorContainer,
            content = MaterialTheme.colorScheme.onErrorContainer
        )
        ButtonType.EQUALS -> ButtonColors(
            container = MaterialTheme.colorScheme.primary,
            content = MaterialTheme.colorScheme.onPrimary
        )
        ButtonType.SCIENTIFIC -> ButtonColors(
            container = MaterialTheme.colorScheme.tertiaryContainer,
            content = MaterialTheme.colorScheme.onTertiaryContainer
        )
    }

    val fontSize = when {
        button.label.length >= 4 -> 16.sp
        button.label.length == 3 -> 18.sp
        button.isScientific -> 20.sp
        else -> 24.sp
    }

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.container,
            contentColor = colors.content
        ),
        shape = RoundedCornerShape(18.dp),
        modifier = modifier
    ) {
        Text(
            text = button.label,
            fontSize = fontSize,
            fontWeight = if (button.type == ButtonType.EQUALS || button.type == ButtonType.OPERATOR)
                FontWeight.Bold else FontWeight.Medium
        )
    }
}
