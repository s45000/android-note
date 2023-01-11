package com.survivalcoding.noteapp.data.repository

import android.content.Context
import android.graphics.Color
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.survivalcoding.noteapp.data.data_source.NoteDao
import com.survivalcoding.noteapp.data.data_source.NoteDatabase
import com.survivalcoding.noteapp.domain.model.Note
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("OPT_IN_USAGE")
@RunWith(AndroidJUnit4::class)
class NoteRepositoryImplTest {

    private lateinit var noteDao: NoteDao
    private lateinit var noteDatabase: NoteDatabase
    private lateinit var noteRepository: NoteRepositoryImpl

    private val noteDummy1 = Note("title1", "body1", Color.RED, System.currentTimeMillis(), id = 1)
    private val noteDummy2 =
        Note("title2", "body2", Color.GREEN, System.currentTimeMillis(), id = 2)
    private val noteDummy3 = Note("title3", "body3", Color.BLUE, System.currentTimeMillis(), id = 3)

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        noteDatabase = Room.inMemoryDatabaseBuilder(
            context, NoteDatabase::class.java
        ).build()
        noteDao = noteDatabase.noteDao
        noteRepository = NoteRepositoryImpl(noteDao)

    }

    @After
    fun tearDown() {
        noteDatabase.close()
    }

    @Test
    fun getNote() = runTest {
        noteRepository.addNote(noteDummy1)
        noteRepository.addNote(noteDummy2)
        noteRepository.addNote(noteDummy3)

        val note1: Note = noteRepository.getNote(1)
        assertEquals(noteDummy1, note1)

        val note2: Note = noteRepository.getNote(2)
        assertEquals(noteDummy2, note2)

        val note3: Note = noteRepository.getNote(3)
        assertEquals(noteDummy3, note3)
    }

    @Test
    fun addNote() = runTest {
        noteRepository.getNotes().test {
            val notes = awaitItem()
            assertEquals(0, notes.size)
        }

        noteRepository.addNote(noteDummy1)
        noteRepository.addNote(noteDummy2)
        noteRepository.getNotes().test {
            val notes = awaitItem()
            assertEquals(2, notes.size)
            assertEquals(true, notes.contains(noteDummy1))
            assertEquals(true, notes.contains(noteDummy2))
        }
    }

    @Test
    fun deleteNote() = runTest {
        noteRepository.getNotes().test {
            val notes = awaitItem()
            assertEquals(0, notes.size)
        }
        noteRepository.addNote(noteDummy1)
        noteRepository.addNote(noteDummy2)
        noteRepository.getNotes().test {
            val notes = awaitItem()
            assertEquals(2, notes.size)
        }
        noteRepository.deleteNote(1)
        noteRepository.getNotes().test {
            val notes = awaitItem()
            assertEquals(1, notes.size)
            assertEquals(false, notes.contains(noteDummy1))
            assertEquals(true, notes.contains(noteDummy2))
        }
        noteRepository.deleteNote(2)
        noteRepository.getNotes().test {
            val notes = awaitItem()
            assertEquals(0, notes.size)
            assertEquals(false, notes.contains(noteDummy1))
            assertEquals(false, notes.contains(noteDummy2))
        }
    }

    @Test
    fun getNotes(): Unit = runTest {
        noteRepository.addNote(noteDummy1)
        noteRepository.addNote(noteDummy2)

        noteRepository.getNotes().test {
            val notes = awaitItem()
            assertEquals(2, notes.size)
            assertEquals(true, notes.contains(noteDummy1))
            assertEquals(true, notes.contains(noteDummy2))
            assertEquals(false, notes.contains(noteDummy3))
        }

        noteRepository.addNote(noteDummy3)
        noteRepository.getNotes().test {
            val notes = awaitItem()
            assertEquals(3, notes.size)
            assertEquals(true, notes.contains(noteDummy1))
            assertEquals(true, notes.contains(noteDummy2))
            assertEquals(true, notes.contains(noteDummy3))
        }
    }
}