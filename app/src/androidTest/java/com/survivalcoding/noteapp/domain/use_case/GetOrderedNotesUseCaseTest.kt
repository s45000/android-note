package com.survivalcoding.noteapp.domain.use_case

import android.content.Context
import android.graphics.Color
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.survivalcoding.noteapp.data.data_source.NoteDao
import com.survivalcoding.noteapp.data.data_source.NoteDatabase
import com.survivalcoding.noteapp.data.repository.NoteRepositoryImpl
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.util.OrderType
import com.survivalcoding.noteapp.domain.util.QueryResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class GetOrderedNotesUseCaseTest {

    private lateinit var noteDao: NoteDao
    private lateinit var noteDatabase: NoteDatabase
    private lateinit var noteRepository: NoteRepositoryImpl


    private lateinit var getOrderedNotesUseCase: GetOrderedNotesUseCase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        noteDatabase = Room.inMemoryDatabaseBuilder(
            context, NoteDatabase::class.java
        ).build()
        noteDao = noteDatabase.noteDao
        noteRepository = NoteRepositoryImpl(noteDao)

        getOrderedNotesUseCase = GetOrderedNotesUseCase(noteRepository)
    }

    @After
    fun tearDown() {
        noteDatabase.close()
    }

    @Test
    operator fun invoke() = runBlocking {
        var queryResult = getOrderedNotesUseCase(OrderType.TITLE, true)
        var notes = (queryResult as QueryResult.Success<Flow<List<Note>>>).value.first()
        assertEquals(emptyList<Note>(), notes)

        val dummyList: MutableList<Note> = ArrayList()
        for (i in 1..10){
            val noteDummy = Note(
                "title ${10-i}",
                "body $i",
                Color.RED+i,
                System.currentTimeMillis(),
                id = i
            )
            delay(50)
            dummyList.add(noteDummy)
            noteRepository.addNote(noteDummy)
        }
        queryResult = getOrderedNotesUseCase(OrderType.TITLE, true)
        dummyList.sortBy { it.title }
        notes = (queryResult as QueryResult.Success<Flow<List<Note>>>).value.first()
        assertEquals(dummyList, notes)

        queryResult = getOrderedNotesUseCase(OrderType.TITLE, false)
        dummyList.sortByDescending { it.title }
        notes = (queryResult as QueryResult.Success<Flow<List<Note>>>).value.first()
        assertEquals(dummyList, notes)

        queryResult = getOrderedNotesUseCase(OrderType.DATE, true)
        dummyList.sortBy { it.date }
        notes = (queryResult as QueryResult.Success<Flow<List<Note>>>).value.first()
        assertEquals(dummyList, notes)

        queryResult = getOrderedNotesUseCase(OrderType.DATE, false)
        dummyList.sortByDescending { it.date }
        notes = (queryResult as QueryResult.Success<Flow<List<Note>>>).value.first()
        assertEquals(dummyList, notes)

        queryResult = getOrderedNotesUseCase(OrderType.COLOR, true)
        dummyList.sortBy { it.color }
        notes = (queryResult as QueryResult.Success<Flow<List<Note>>>).value.first()
        assertEquals(dummyList, notes)

        queryResult = getOrderedNotesUseCase(OrderType.COLOR, false)
        dummyList.sortByDescending { it.color }
        notes = (queryResult as QueryResult.Success<Flow<List<Note>>>).value.first()
        assertEquals(dummyList, notes)
    }
}