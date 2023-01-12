package com.survivalcoding.noteapp.presentation.ui_state

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.util.OrderType
import kotlinx.coroutines.flow.Flow

data class NoteListUiState(
    val notes: Flow<List<Note>>,
    var orderType: OrderType,
    var isAscending: Boolean,
)