package com.example.lab4kalkulkator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorDisplay(
    expression: String,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    // Scroll to the end automatically when new characters are added
    LaunchedEffect(expression) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }

    val isEmpty = expression == ""

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .padding(horizontal = 24.dp, vertical = 20.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Text(
            text = if (isEmpty) "0" else expression,
            style = MaterialTheme.typography.displayMedium.copy(
                color = if (isEmpty)
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                else
                    MaterialTheme.colorScheme.onSurface,
                fontSize = 38.sp,
                textAlign = TextAlign.End
            ),
            maxLines = 1,
            modifier = Modifier.horizontalScroll(scrollState)
        )
    }
}
