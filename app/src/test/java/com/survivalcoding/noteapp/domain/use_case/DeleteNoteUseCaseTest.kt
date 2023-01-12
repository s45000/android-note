package com.survivalcoding.noteapp.domain.use_case

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.util.QueryResult
import com.survivalcoding.noteapp.mock.MockNoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test


class DeleteNoteUseCaseTest {

    val deleteNoteUseCase = DeleteNoteUseCase(MockNoteRepository())

    @Test
    operator fun invoke() = runBlocking {
        var queryResult = deleteNoteUseCase(Note(id = 0))
        assertEquals("존재하지 않는 Note", (queryResult as QueryResult.Fail).msg)

        queryResult = deleteNoteUseCase(Note(id = 1))
        assertEquals(true, queryResult is QueryResult.Success)

        queryResult = deleteNoteUseCase(Note(id = 2))
        assertEquals(true, queryResult is QueryResult.Fail)
    }
}