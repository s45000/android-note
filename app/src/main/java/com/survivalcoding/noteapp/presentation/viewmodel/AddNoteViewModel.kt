package com.survivalcoding.noteapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.NoteColor
import com.survivalcoding.noteapp.domain.use_case.NoteUseCases
import com.survivalcoding.noteapp.presentation.ui_event.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel
@Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    suspend fun add(title: String, body: String, color: Long) {
        if (title.isBlank()) {
            _uiEvent.emit(UiEvent.ShowSnackBar("The title of the note can't be empty."))
            return
        }
        val date = System.currentTimeMillis()
        val newNote = Note(title, body, NoteColor.fromLong(color), date)

        noteUseCases.addNoteUseCase(newNote)
        _uiEvent.emit(UiEvent.SaveSuccess)
    }
}