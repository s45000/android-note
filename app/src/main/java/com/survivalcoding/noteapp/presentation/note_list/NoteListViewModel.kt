package com.survivalcoding.noteapp.presentation.note_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.noteapp.Config
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.use_case.NoteUseCases
import com.survivalcoding.noteapp.domain.util.OrderType
import com.survivalcoding.noteapp.domain.util.QueryResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
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

    private val _snackBarEvent = MutableSharedFlow<String>()
    val snackBarEvent = _snackBarEvent.asSharedFlow()

    private var deletedNote: Note? = null

    private val _menuToggleUiState: MutableStateFlow<Boolean> =
        MutableStateFlow(
            false
        )


    val noteListUiState = _noteListUiState.asStateFlow()
    val menuToggleUiState = _menuToggleUiState.asStateFlow()

    private var loadJob: Job? = null
    fun load(orderType: OrderType, isAscending: Boolean) {
        loadJob?.cancel()

        val queryResult = noteUseCases.getOrderedNotesUseCase(orderType, isAscending)
        loadJob = (queryResult as QueryResult.Success).value.onEach {
            _noteListUiState.value = noteListUiState.value.copy(notes = it)
        }.launchIn(viewModelScope)
    }

    fun delete(note: Note) {
        viewModelScope.launch {
            noteUseCases.deleteNoteUseCase(note)
            load(noteListUiState.value.orderType, noteListUiState.value.isAscending)
            deletedNote = note
            _snackBarEvent.emit("Note deleted")
        }
    }

    fun undo() {
        deletedNote?.let {
            viewModelScope.launch {
                noteUseCases.addNoteUseCase(it)
                deletedNote = null
            }
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