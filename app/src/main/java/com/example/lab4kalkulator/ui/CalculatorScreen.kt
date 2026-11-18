package com.example.lab4kalkulator.ui

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab4kalkulator.CalculatorViewModel

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel,
    modifier: Modifier = Modifier
) {
    val expression by viewModel.expression.collectAsState()
    val result by viewModel.result.collectAsState()
    val isError by viewModel.isError.collectAsState()

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    var showScientific by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        if (isLandscape) {
            CalculatorLandscapeLayout(
                expression = expression,
                result = result,
                isError = isError,
                viewModel = viewModel,
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            CalculatorPortraitLayout(
                expression = expression,
                result = result,
                isError = isError,
                showScientific = showScientific,
                onToggleScientific = { showScientific = !showScientific },
                viewModel = viewModel,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
private fun DisplaySection(
    expression: String,
    result: String,
    isError: Boolean,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            // Wprowadzane wyrażenie
            Text(
                text = if (expression.isEmpty()) "0" else expression,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 28.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Wynik obliczeń lub komunikat o błędzie
            Text(
                text = result,
                style = MaterialTheme.typography.displayMedium.copy(
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun CalculatorPortraitLayout(
    expression: String,
    result: String,
    isError: Boolean,
    showScientific: Boolean,
    onToggleScientific: () -> Unit,
    viewModel: CalculatorViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        DisplaySection(
            expression = expression,
            result = result,
            isError = isError,
            modifier = Modifier.weight(1f)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Przycisk przełączania paska naukowego
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Surface(
                    onClick = onToggleScientific,
                    shape = CircleShape,
                    color = if (showScientific) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = if (showScientific) "Ukryj f(x)" else "Pokaż f(x)",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = if (showScientific) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Panel z funkcjami naukowymi (rozwijany)
            AnimatedVisibility(
                visible = showScientific,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                ScientificPanel(viewModel = viewModel)
            }

            // Standardowa siatka przycisków
            StandardKeypad(viewModel = viewModel)
        }
    }
}

@Composable
private fun CalculatorLandscapeLayout(
    expression: String,
    result: String,
    isError: Boolean,
    viewModel: CalculatorViewModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DisplaySection(
            expression = expression,
            result = result,
            isError = isError,
            modifier = Modifier
                .weight(1.2f)
                .fillMaxHeight()
        )

        Row(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ScientificPanelLandscape(viewModel = viewModel)
            }

            Column(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StandardKeypad(viewModel = viewModel)
            }
        }
    }
}

@Composable
private fun ScientificPanel(viewModel: CalculatorViewModel) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            CalcButton("sin", Modifier.weight(1f), isFunction = true) { viewModel.onFunctionInput("sin") }
            CalcButton("cos", Modifier.weight(1f), isFunction = true) { viewModel.onFunctionInput("cos") }
            CalcButton("tan", Modifier.weight(1f), isFunction = true) { viewModel.onFunctionInput("tan") }
            CalcButton("sqrt", Modifier.weight(1f), isFunction = true) { viewModel.onFunctionInput("sqrt") }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            CalcButton("ln", Modifier.weight(1f), isFunction = true) { viewModel.onFunctionInput("ln") }
            CalcButton("log", Modifier.weight(1f), isFunction = true) { viewModel.onFunctionInput("log10") }
            CalcButton("^", Modifier.weight(1f), isOperator = true) { viewModel.onCharacterInput("^") }
            CalcButton("pi", Modifier.weight(1f), isFunction = true) { viewModel.onCharacterInput("pi") }
        }
    }
}

@Composable
private fun ScientificPanelLandscape(viewModel: CalculatorViewModel) {
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            CalcButton("sin", Modifier.weight(1f), isFunction = true) { viewModel.onFunctionInput("sin") }
            CalcButton("cos", Modifier.weight(1f), isFunction = true) { viewModel.onFunctionInput("cos") }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            CalcButton("tan", Modifier.weight(1f), isFunction = true) { viewModel.onFunctionInput("tan") }
            CalcButton("sqrt", Modifier.weight(1f), isFunction = true) { viewModel.onFunctionInput("sqrt") }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            CalcButton("ln", Modifier.weight(1f), isFunction = true) { viewModel.onFunctionInput("ln") }
            CalcButton("log", Modifier.weight(1f), isFunction = true) { viewModel.onFunctionInput("log10") }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            CalcButton("^", Modifier.weight(1f), isOperator = true) { viewModel.onCharacterInput("^") }
            CalcButton("pi", Modifier.weight(1f), isFunction = true) { viewModel.onCharacterInput("pi") }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            CalcButton("(", Modifier.weight(1f), isOperator = true) { viewModel.onCharacterInput("(") }
            CalcButton(")", Modifier.weight(1f), isOperator = true) { viewModel.onCharacterInput(")") }
        }
    }
}

@Composable
private fun StandardKeypad(viewModel: CalculatorViewModel) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalcButton("AC", Modifier.weight(1f), isAction = true) { viewModel.onClear() }
            CalcButton("DEL", Modifier.weight(1f), isAction = true) { viewModel.onDelete() }
            CalcButton("(", Modifier.weight(1f), isOperator = true) { viewModel.onCharacterInput("(") }
            CalcButton(")", Modifier.weight(1f), isOperator = true) { viewModel.onCharacterInput(")") }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalcButton("7", Modifier.weight(1f)) { viewModel.onCharacterInput("7") }
            CalcButton("8", Modifier.weight(1f)) { viewModel.onCharacterInput("8") }
            CalcButton("9", Modifier.weight(1f)) { viewModel.onCharacterInput("9") }
            CalcButton("/", Modifier.weight(1f), isOperator = true) { viewModel.onCharacterInput("/") }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalcButton("4", Modifier.weight(1f)) { viewModel.onCharacterInput("4") }
            CalcButton("5", Modifier.weight(1f)) { viewModel.onCharacterInput("5") }
            CalcButton("6", Modifier.weight(1f)) { viewModel.onCharacterInput("6") }
            CalcButton("*", Modifier.weight(1f), isOperator = true) { viewModel.onCharacterInput("*") }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalcButton("1", Modifier.weight(1f)) { viewModel.onCharacterInput("1") }
            CalcButton("2", Modifier.weight(1f)) { viewModel.onCharacterInput("2") }
            CalcButton("3", Modifier.weight(1f)) { viewModel.onCharacterInput("3") }
            CalcButton("-", Modifier.weight(1f), isOperator = true) { viewModel.onCharacterInput("-") }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalcButton("0", Modifier.weight(1.5f)) { viewModel.onCharacterInput("0") }
            CalcButton(".", Modifier.weight(1f)) { viewModel.onCharacterInput(".") }
            CalcButton("+", Modifier.weight(1f), isOperator = true) { viewModel.onCharacterInput("+") }
            CalcButton("=", Modifier.weight(1f), isEquals = true) { viewModel.onCalculate() }
        }
    }
}

@Composable
private fun CalcButton(
    text: String,
    modifier: Modifier = Modifier,
    isOperator: Boolean = false,
    isFunction: Boolean = false,
    isAction: Boolean = false,
    isEquals: Boolean = false,
    onClick: () -> Unit
) {
    val containerColor = when {
        isEquals -> MaterialTheme.colorScheme.primary
        isAction -> MaterialTheme.colorScheme.errorContainer
        isOperator -> MaterialTheme.colorScheme.secondaryContainer
        isFunction -> MaterialTheme.colorScheme.tertiaryContainer
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    val contentColor = when {
        isEquals -> MaterialTheme.colorScheme.onPrimary
        isAction -> MaterialTheme.colorScheme.onErrorContainer
        isOperator -> MaterialTheme.colorScheme.onSecondaryContainer
        isFunction -> MaterialTheme.colorScheme.onTertiaryContainer
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Button(
        onClick = onClick,
        modifier = modifier.height(58.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Text(
            text = text,
            fontSize = if (text.length > 3) 14.sp else 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
