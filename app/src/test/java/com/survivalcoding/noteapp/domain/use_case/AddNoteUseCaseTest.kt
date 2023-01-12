package com.survivalcoding.noteapp.domain.use_case

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.util.QueryResult
import com.survivalcoding.noteapp.mock.MockNoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class AddNoteUseCaseTest {

    val addNoteUseCase = AddNoteUseCase(MockNoteRepository())

    @Test
    operator fun invoke() = runBlocking {
        var queryResult = addNoteUseCase(Note())
        assertEquals(true, queryResult is QueryResult.Success)

        queryResult = addNoteUseCase(Note(id = -1))
        assertEquals(true, queryResult is QueryResult.Fail)
    }
}