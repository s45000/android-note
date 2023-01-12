package com.survivalcoding.noteapp.mock

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockNoteRepository : NoteRepository {
    override suspend fun getNote(id: Int): Note? {
        if (id == -1) throw Exception()
        else if (id == 0) return null
        return Note()
    }

    override suspend fun addNote(note: Note) {
        if (note.id == -1) throw Exception()
        return
    }

    override suspend fun deleteNote(note: Note): Int {
        if (note.id == 0) return 0
        else if (note.id == 1) return 1
        throw Exception()
    }

    override suspend fun updateNote(note: Note): Int {
        if (note.id == 0) return 0
        else if (note.id == 1) return 1
        throw Exception()
    }

    override fun getNotes(): Flow<List<Note>> {

        val dummyList = mutableListOf<Note>()
        for (i in 1..10) {
            val noteDummy = Note(
                "title $i",
                "body $i",
                i,
                i.toLong(),
                id = i
            )
            dummyList.add(noteDummy)
        }

        return flow {
            emit(dummyList)
        }

    }

}