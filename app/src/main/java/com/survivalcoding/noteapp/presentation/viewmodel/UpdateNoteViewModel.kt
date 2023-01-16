package com.survivalcoding.noteapp.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.NoteColor
import com.survivalcoding.noteapp.domain.use_case.NoteUseCases
import com.survivalcoding.noteapp.domain.util.QueryResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    fun update(title: String, body: String, color: Long) {
        println("update : ${noteState.value.id} $title $body $color")
        viewModelScope.launch {
            val ql = noteUseCases.updateNoteUseCase(
                noteState.value.copy(
                    title = title,
                    body = body,
                    color = NoteColor.fromLong(color),
                    date = System.currentTimeMillis()
                )
            )
            when (ql) {
                is QueryResult.Fail -> println(ql.msg)
                is QueryResult.Success -> println(ql.value)
            }
        }
    }
}