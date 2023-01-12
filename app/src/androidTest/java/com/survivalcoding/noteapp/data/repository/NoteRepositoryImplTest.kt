package com.survivalcoding.noteapp.data.repository

import android.graphics.Color
import com.survivalcoding.noteapp.data.data_source.NoteDatabase
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.robolectric.annotation.Config
import javax.inject.Inject

@HiltAndroidTest
@Config(application = HiltTestApplication::class)
class NoteRepositoryImplTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var noteDatabase: NoteDatabase

    @Inject
    lateinit var noteRepository: NoteRepository

    private val noteDummy1 = Note(
        "title1",
        "body1",
        Color.RED,
        System.currentTimeMillis(),
        id = 1
    )
    private val noteDummy2 = Note(
        "title2",
        "body2",
        Color.GREEN,
        System.currentTimeMillis(),
        id = 2
    )
    private val noteDummy3 = Note(
        "title3",
        "body3",
        Color.BLUE,
        System.currentTimeMillis(),
        id = 3
    )

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        noteDatabase.close()
    }

    @Test
    fun getNote() = runBlocking {
        noteRepository.addNote(noteDummy1)
        noteRepository.addNote(noteDummy2)
        noteRepository.addNote(noteDummy3)

        val note1: Note? = noteRepository.getNote(1)
        assertEquals(noteDummy1, note1)

        val note2: Note? = noteRepository.getNote(2)
        assertEquals(noteDummy2, note2)

        val note3: Note? = noteRepository.getNote(3)
        assertEquals(noteDummy3, note3)
    }

    @Test
    fun addNote() = runBlocking {
        var notes = noteRepository.getNotes().first()
        assertEquals(0, notes.size)

        noteRepository.addNote(noteDummy1)
        noteRepository.addNote(noteDummy2)

        notes = noteRepository.getNotes().first()
        assertEquals(2, notes.size)
        assertEquals(true, notes.contains(noteDummy1))
        assertEquals(true, notes.contains(noteDummy2))
    }

    @Test
    fun deleteNote() = runBlocking {
        var notes = noteRepository.getNotes().first()
        assertEquals(0, notes.size)

        noteRepository.addNote(noteDummy1)
        noteRepository.addNote(noteDummy2)

        notes = noteRepository.getNotes().first()
        assertEquals(2, notes.size)

        noteRepository.deleteNote(noteDummy1)

        notes = noteRepository.getNotes().first()
        assertEquals(1, notes.size)
        assertEquals(false, notes.contains(noteDummy1))
        assertEquals(true, notes.contains(noteDummy2))


        noteRepository.deleteNote(noteDummy2)
        notes = noteRepository.getNotes().first()
        assertEquals(0, notes.size)
        assertEquals(false, notes.contains(noteDummy1))
        assertEquals(false, notes.contains(noteDummy2))
    }

    @Test
    fun getNotes(): Unit = runBlocking {
        noteRepository.addNote(noteDummy1)
        noteRepository.addNote(noteDummy2)

        var notes = noteRepository.getNotes().first()
        assertEquals(2, notes.size)
        assertEquals(true, notes.contains(noteDummy1))
        assertEquals(true, notes.contains(noteDummy2))
        assertEquals(false, notes.contains(noteDummy3))

        noteRepository.addNote(noteDummy3)
        notes = noteRepository.getNotes().first()
        assertEquals(3, notes.size)
        assertEquals(true, notes.contains(noteDummy1))
        assertEquals(true, notes.contains(noteDummy2))
        assertEquals(true, notes.contains(noteDummy3))
    }

    @Test
    fun updateNote(): Unit = runBlocking {
        noteRepository.addNote(noteDummy1)
        noteRepository.addNote(noteDummy2)
        
        val noteDummy4 = noteDummy2.copy(
            title = "change"
        )


        assertEquals(1, noteRepository.updateNote(noteDummy4))
        assertEquals(noteDummy4, noteRepository.getNote(noteDummy2.id))
    }
}