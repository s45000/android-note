package com.survivalcoding.noteapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.NoteColor
import com.survivalcoding.noteapp.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel
@Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {
    fun add(title: String, body: String, color: Long) {
        viewModelScope.launch {
            val date = System.currentTimeMillis()
            val newNote = Note(title, body, NoteColor.fromLong(color), date)
            noteUseCases.addNoteUseCase(newNote)
        }
    }
}