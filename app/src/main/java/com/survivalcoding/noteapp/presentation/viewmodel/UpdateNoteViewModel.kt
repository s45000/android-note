package com.survivalcoding.noteapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateNoteViewModel
@Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    fun update(note: Note) {
        viewModelScope.launch {
            noteUseCases.updateNoteUseCase(note)
        }
    }
}