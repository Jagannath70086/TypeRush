package com.typer.typerush.typetest.presentation

interface TypeTestEvent {
    object Submitted : TypeTestEvent
    data class Error(val message: String) : TypeTestEvent
}