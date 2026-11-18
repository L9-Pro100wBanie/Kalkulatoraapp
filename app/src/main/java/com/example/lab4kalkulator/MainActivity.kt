package com.example.lab4kalkulator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab4kalkulator.ui.CalculatorScreen
import com.example.lab4kalkulator.ui.theme.Lab4kalkulatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab4kalkulatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val calculatorViewModel: CalculatorViewModel = viewModel()
                    CalculatorScreen(
                        viewModel = calculatorViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
