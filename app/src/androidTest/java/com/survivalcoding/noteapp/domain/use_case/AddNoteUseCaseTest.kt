package com.survivalcoding.noteapp.domain.use_case

import android.content.Context
import android.graphics.Color
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.survivalcoding.noteapp.data.data_source.NoteDao
import com.survivalcoding.noteapp.data.data_source.NoteDatabase
import com.survivalcoding.noteapp.data.repository.NoteRepositoryImpl
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.util.QueryResult
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AddNoteUseCaseTest {

    private lateinit var noteDao: NoteDao
    private lateinit var noteDatabase: NoteDatabase
    private lateinit var noteRepository: NoteRepositoryImpl


    private lateinit var addNoteUseCase: AddNoteUseCase

    private val noteDummy1 = Note(
        "title1",
        "body1",
        Color.RED,
        System.currentTimeMillis(),
        id = 1
    )

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        noteDatabase = Room.inMemoryDatabaseBuilder(
            context, NoteDatabase::class.java
        ).build()
        noteDao = noteDatabase.noteDao
        noteRepository = NoteRepositoryImpl(noteDao)

        addNoteUseCase = AddNoteUseCase(noteRepository)
    }

    @After
    fun tearDown() {
        noteDatabase.close()
    }

    @Test
    operator fun invoke() = runBlocking {
        var queryResult = addNoteUseCase(noteDummy1)
        assertEquals(true, queryResult is QueryResult.Success<*>)
        queryResult = addNoteUseCase(noteDummy1)
        assertEquals(true, queryResult is QueryResult.Fail)
    }
}