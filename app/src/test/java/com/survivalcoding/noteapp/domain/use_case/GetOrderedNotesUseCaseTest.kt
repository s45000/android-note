package com.survivalcoding.noteapp.domain.use_case

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.util.OrderType
import com.survivalcoding.noteapp.domain.util.QueryResult
import com.survivalcoding.noteapp.mock.MockNoteRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetOrderedNotesUseCaseTest {

    val getOrderedNotesUseCase = GetOrderedNotesUseCase(MockNoteRepository())

    @Test
    operator fun invoke() = runBlocking {
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

        var queryResult = getOrderedNotesUseCase(OrderType.TITLE, true)
        dummyList.sortBy { it.title }
        var notes = (queryResult as QueryResult.Success).value.first()
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