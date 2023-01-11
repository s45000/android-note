package com.survivalcoding.noteapp.presentation

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.util.OrderType
import kotlinx.coroutines.flow.Flow

data class MainUiState(
    val notes: Flow<List<Note>>,
    var selectedNote: Note?,
    var orderType: OrderType,
    var isAscending: Boolean,
)