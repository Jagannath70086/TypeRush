package com.typer.typerush.typetest.presentation

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.typer.typerush.navigation.NavigationManager
import kotlinx.coroutines.runBlocking
import org.koin.compose.koinInject

@Composable
fun TypeTestScreen(
    id: String,
    navigationManager: NavigationManager = koinInject()
) {
    Button(onClick = {
        runBlocking { navigationManager.navigateBack() }
    }) {
        Text("Back")
    }
    Text("Type Test Screen $id")
}