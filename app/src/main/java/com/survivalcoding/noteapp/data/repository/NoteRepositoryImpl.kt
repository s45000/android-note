package com.survivalcoding.noteapp.data.repository

import com.survivalcoding.noteapp.data.data_source.NoteDao
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {
    override suspend fun getNote(id: Int): Note {
        return noteDao.getNote(id)
    }

    override suspend fun addNote(note: Note) {
        noteDao.addNote(note)
    }

    override suspend fun deleteNote(id: Int) {
        noteDao.deleteNote(id)
    }

    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotes()
    }
}