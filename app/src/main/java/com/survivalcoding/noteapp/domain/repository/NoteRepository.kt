package com.survivalcoding.noteapp.domain.repository

import com.survivalcoding.noteapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun getNote(id: Int): Note?
    suspend fun addNote(note: Note)
    suspend fun deleteNote(note: Note): Int
    suspend fun updateNote(note: Note): Int
    fun getNotes(): Flow<List<Note>>
}