package com.survivalcoding.noteapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.noteapp.Config
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.NoteColor
import com.survivalcoding.noteapp.domain.use_case.NoteUseCases
import com.survivalcoding.noteapp.domain.util.OrderType
import com.survivalcoding.noteapp.presentation.ui_state.NoteListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel
@Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {
    private val _noteListUiState: MutableStateFlow<NoteListUiState> =
        MutableStateFlow(
            NoteListUiState(
                emptyList(), Config.DEFAULT_ORDER_TYPE, Config.DEFAULT_IS_ASCENDING
            )
        )


    init {
        load(Config.DEFAULT_ORDER_TYPE, Config.DEFAULT_IS_ASCENDING)
    }

    val noteListUiState = _noteListUiState.asStateFlow()

    fun load(orderType: OrderType, isAscending: Boolean) {
//        val queryResult = noteUseCases.getOrderedNotesUseCase(orderType, isAscending)
//        val notes = (queryResult as QueryResult.Success).value.onEach {
//            _noteListUiState.value = noteListUiState.value.copy(notes = it)
//        }.launchIn(viewModelScope)
        viewModelScope.launch {
            _noteListUiState.value = noteListUiState.value.copy(
                notes = listOf(
                    Note("test", "test", NoteColor.BabyBlue, System.currentTimeMillis(), 0),
                    Note("test", "test", NoteColor.RedPink, System.currentTimeMillis(), 1),
                    Note("test", "test", NoteColor.RedOrange, System.currentTimeMillis(), 2)
                )
            )
        }
    }

    fun delete(note: Note) {
        viewModelScope.launch {
            noteUseCases.deleteNoteUseCase(note)
            load(noteListUiState.value.orderType, noteListUiState.value.isAscending)
        }
    }

    fun changeOrderType(orderType: OrderType) {
        _noteListUiState.value = noteListUiState.value.copy(orderType = orderType)
        load(noteListUiState.value.orderType, noteListUiState.value.isAscending)
    }

    fun changeIsAscending(isAscending: Boolean) {
        _noteListUiState.value = noteListUiState.value.copy(isAscending = isAscending)
        load(noteListUiState.value.orderType, noteListUiState.value.isAscending)
    }
}