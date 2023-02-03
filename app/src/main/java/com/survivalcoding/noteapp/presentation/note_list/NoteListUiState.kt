package com.survivalcoding.noteapp.presentation.note_list

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.util.OrderType

data class NoteListUiState(
    val notes: List<Note>,
    var orderType: OrderType,
    var isAscending: Boolean,
)