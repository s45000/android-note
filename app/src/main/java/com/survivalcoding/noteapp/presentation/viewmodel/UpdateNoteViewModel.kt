package com.survivalcoding.noteapp.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.NoteColor
import com.survivalcoding.noteapp.domain.use_case.NoteUseCases
import com.survivalcoding.noteapp.domain.util.QueryResult
import com.survivalcoding.noteapp.presentation.ui_event.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateNoteViewModel
@Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _noteState = MutableStateFlow(Note())
    val noteState: StateFlow<Note> = _noteState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            viewModelScope.launch {
                when (val queryResult = noteUseCases.getNoteUseCase(noteId)) {
                    is QueryResult.Fail -> TODO()
                    is QueryResult.Success -> {
                        val note = queryResult.value
                        _noteState.value = note
                    }
                }
            }
        }
    }

    fun tempSave(title: String, body: String, color: Long) {
        _noteState.value = noteState.value.copy(
            title = title,
            body = body,
            color = NoteColor.fromLong(color),
        )
    }

    suspend fun update(title: String, body: String, color: Long) {
        if (title.isBlank()) {
            _uiEvent.emit(UiEvent.ShowSnackBar("The title of the note can't be empty."))
            return
        }

        noteUseCases.updateNoteUseCase(
            noteState.value.copy(
                title = title,
                body = body,
                color = NoteColor.fromLong(color),
                date = System.currentTimeMillis()
            )
        )
        _uiEvent.emit(UiEvent.SaveSuccess)
    }
}