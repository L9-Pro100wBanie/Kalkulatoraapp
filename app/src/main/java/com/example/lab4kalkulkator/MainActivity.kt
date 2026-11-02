package com.example.lab4kalkulkator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.lab4kalkulkator.ui.CalculatorScreen
import com.example.lab4kalkulkator.ui.theme.Lab4kalkulkatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab4kalkulkatorTheme {
                CalculatorScreen()
            }
        }
    }
}
