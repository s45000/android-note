package com.survivalcoding.noteapp.presentation.ui_event

sealed class UiEvent {
    class ShowSnackBar(val msg: String) : UiEvent()
    object SaveSuccess : UiEvent()
}