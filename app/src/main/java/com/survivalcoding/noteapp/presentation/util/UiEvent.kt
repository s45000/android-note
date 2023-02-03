package com.survivalcoding.noteapp.presentation.util

sealed class UiEvent {
    class ShowSnackBar(val msg: String) : UiEvent()
    object SaveSuccess : UiEvent()
}