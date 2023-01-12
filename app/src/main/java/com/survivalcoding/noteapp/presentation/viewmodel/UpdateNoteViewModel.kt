package com.survivalcoding.noteapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.noteapp.Config
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.use_case.NoteUseCases
import com.survivalcoding.noteapp.domain.util.QueryResult
import com.survivalcoding.noteapp.presentation.ui_state.NoteListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateNoteViewModel
@Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {
    private val _updateNoteUiState: MutableStateFlow<NoteListUiState> by lazy {
        val queryResult = noteUseCases.getOrderedNotesUseCase(
            Config.DEFAULT_ORDER_TYPE,
            Config.DEFAULT_IS_ASCENDING
        )
        val notes = (queryResult as QueryResult.Success).value
        MutableStateFlow(
            NoteListUiState(
                notes,
                Config.DEFAULT_ORDER_TYPE,
                Config.DEFAULT_IS_ASCENDING
            )
        )
    }
    val updateNoteUiState = _updateNoteUiState.asStateFlow()

    fun update(note: Note) {
        viewModelScope.launch {
            noteUseCases.updateNoteUseCase(note)
        }
    }
}