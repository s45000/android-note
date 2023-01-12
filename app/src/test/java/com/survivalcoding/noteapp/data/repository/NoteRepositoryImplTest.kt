package com.survivalcoding.noteapp.data.repository

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.mock.MockNoteDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NoteRepositoryImplTest {

    private val noteRepositoryImpl = NoteRepositoryImpl(MockNoteDao())

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getNote() = runBlocking {
        assertEquals(null, noteRepositoryImpl.getNote(0))
        assertEquals(Note(), noteRepositoryImpl.getNote(1))
    }

    @Test
    fun addNote() = runBlocking {
        noteRepositoryImpl.addNote(Note())
    }

    @Test
    fun deleteNote() = runBlocking {
        assertEquals(0, noteRepositoryImpl.deleteNote(Note(id = 0)))
        assertEquals(1, noteRepositoryImpl.deleteNote(Note(id = 1)))
        assertEquals(2, noteRepositoryImpl.deleteNote(Note(id = 2)))
    }

    @Test
    fun updateNote() = runBlocking {
        assertEquals(0, noteRepositoryImpl.updateNote(Note(id = 0)))
        assertEquals(1, noteRepositoryImpl.updateNote(Note(id = 1)))
    }

    @Test
    fun getNotes() = runBlocking {
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
        val notes = noteRepositoryImpl.getNotes().first()
        assertEquals(dummyList, notes)
    }
}