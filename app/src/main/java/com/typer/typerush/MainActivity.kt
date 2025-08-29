package com.typer.typerush

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.typer.typerush.navigation.NavigationStack
import com.typer.typerush.ui.theme.TypeRushTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TypeRushTheme {
                NavigationStack()
            }
        }
    }
}