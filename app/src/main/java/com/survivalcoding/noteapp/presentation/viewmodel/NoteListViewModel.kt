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
    private val _menuToggleUiState: MutableStateFlow<Boolean> =
        MutableStateFlow(
            false
        )

    init {
        load(Config.DEFAULT_ORDER_TYPE, Config.DEFAULT_IS_ASCENDING)
    }

    val noteListUiState = _noteListUiState.asStateFlow()
    val menuToggleUiState = _menuToggleUiState.asStateFlow()

    fun load(orderType: OrderType, isAscending: Boolean) {
//        val queryResult = noteUseCases.getOrderedNotesUseCase(orderType, isAscending)
//        val notes = (queryResult as QueryResult.Success).value.onEach {
//            _noteListUiState.value = noteListUiState.value.copy(notes = it)
//        }.launchIn(viewModelScope)
        viewModelScope.launch {
            _noteListUiState.value = noteListUiState.value.copy(
                notes = listOf(
                    Note("test1", "test", NoteColor.BabyBlue, 1, 0),
                    Note("test2", "test", NoteColor.RedPink, 3, 1),
                    Note("test3", "test", NoteColor.RedOrange, 2, 2)
                ).let { notes ->
                    when (orderType) {
                        OrderType.TITLE -> {
                            if (isAscending) {
                                notes.sortedBy { it.title }
                            } else {
                                notes.sortedByDescending { it.title }
                            }
                        }
                        OrderType.DATE -> {
                            if (isAscending) {
                                notes.sortedBy { it.date }
                            } else {
                                notes.sortedByDescending { it.date }
                            }
                        }
                        OrderType.COLOR -> {
                            if (isAscending) {
                                notes.sortedBy { it.color }
                            } else {
                                notes.sortedByDescending { it.color }
                            }
                        }
                    }
                }
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

    fun menuSelectToggle() {
        _menuToggleUiState.value = !menuToggleUiState.value
    }
}