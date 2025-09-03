package com.typer.typerush.typetest.presentation

interface TypeTestIntent {
    data class OnTypedValueChanged(val newValue: String) : TypeTestIntent
    data class SetType(val type: String) : TypeTestIntent
    object DismissError : TypeTestIntent
    object DismissSuccess : TypeTestIntent
}