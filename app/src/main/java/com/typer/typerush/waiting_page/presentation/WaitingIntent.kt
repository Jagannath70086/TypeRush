package com.typer.typerush.waiting_page.presentation

import androidx.compose.ui.platform.ClipboardManager

sealed interface WaitingIntent {
    object OnStartPressed: WaitingIntent
    object OnActuallyStarted: WaitingIntent
    object OnBackPressed: WaitingIntent
    object DismissError: WaitingIntent
    object DismissSuccess: WaitingIntent
    class OnCodeCopied(val clipboardManager: ClipboardManager): WaitingIntent
    object DismissStartDialog: WaitingIntent
}