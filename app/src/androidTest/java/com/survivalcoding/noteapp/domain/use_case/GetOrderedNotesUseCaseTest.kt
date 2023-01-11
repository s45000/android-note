package com.survivalcoding.noteapp.domain.use_case

import android.graphics.Color
import com.survivalcoding.noteapp.data.data_source.NoteDatabase
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import com.survivalcoding.noteapp.domain.util.OrderType
import com.survivalcoding.noteapp.domain.util.QueryResult
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.delay
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
class GetOrderedNotesUseCaseTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var noteDatabase: NoteDatabase

    @Inject
    lateinit var noteRepository: NoteRepository

    private lateinit var getOrderedNotesUseCase: GetOrderedNotesUseCase

    @Before
    fun setUp() {
        hiltRule.inject()
        getOrderedNotesUseCase = GetOrderedNotesUseCase(noteRepository)
    }

    @After
    fun tearDown() {
        noteDatabase.close()
    }

    @Test
    operator fun invoke() = runBlocking {
        var queryResult = getOrderedNotesUseCase(OrderType.TITLE, true)
        var notes = (queryResult as QueryResult.Success).value.first()
        assertEquals(emptyList<Note>(), notes)

        val dummyList: MutableList<Note> = ArrayList()
        for (i in 1..10) {
            val noteDummy = Note(
                "title ${10 - i}",
                "body $i",
                Color.RED + i,
                System.currentTimeMillis(),
                id = i
            )
            delay(50)
            dummyList.add(noteDummy)
            noteRepository.addNote(noteDummy)
        }
        queryResult = getOrderedNotesUseCase(OrderType.TITLE, true)
        dummyList.sortBy { it.title }
        notes = (queryResult as QueryResult.Success).value.first()
        assertEquals(dummyList, notes)

        queryResult = getOrderedNotesUseCase(OrderType.TITLE, false)
        dummyList.sortByDescending { it.title }
        notes = (queryResult as QueryResult.Success).value.first()
        assertEquals(dummyList, notes)

        queryResult = getOrderedNotesUseCase(OrderType.DATE, true)
        dummyList.sortBy { it.date }
        notes = (queryResult as QueryResult.Success).value.first()
        assertEquals(dummyList, notes)

        queryResult = getOrderedNotesUseCase(OrderType.DATE, false)
        dummyList.sortByDescending { it.date }
        notes = (queryResult as QueryResult.Success).value.first()
        assertEquals(dummyList, notes)

        queryResult = getOrderedNotesUseCase(OrderType.COLOR, true)
        dummyList.sortBy { it.color }
        notes = (queryResult as QueryResult.Success).value.first()
        assertEquals(dummyList, notes)

        queryResult = getOrderedNotesUseCase(OrderType.COLOR, false)
        dummyList.sortByDescending { it.color }
        notes = (queryResult as QueryResult.Success).value.first()
        assertEquals(dummyList, notes)
    }
}