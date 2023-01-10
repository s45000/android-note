package com.survivalcoding.noteapp

import android.app.Application
import com.survivalcoding.noteapp.data.data_source.NoteDatabase
import com.survivalcoding.noteapp.data.repository.NoteRepositoryImpl
import com.survivalcoding.noteapp.domain.use_case.*

class NoteApplication : Application() {
    private val noteDatabase = NoteDatabase.getDatabase(this)
    private val noteRepository = NoteRepositoryImpl(noteDatabase.noteDao)
    private val addNoteUseCase = AddNoteUseCase(noteRepository)
    private val deleteNoteUseCase = DeleteNoteUseCase(noteRepository)
    private val getNoteUseCase = GetNoteUseCase(noteRepository)
    private val getOrderedNoteUseCase = GetOrderedNotesUseCase(noteRepository)
    private val updateNoteUseCase = UpdateNoteUseCase(noteRepository)

    val noteUseCases = NoteUseCases(
        addNoteUseCase,
        getNoteUseCase,
        deleteNoteUseCase,
        updateNoteUseCase,
        getOrderedNoteUseCase
    )
}