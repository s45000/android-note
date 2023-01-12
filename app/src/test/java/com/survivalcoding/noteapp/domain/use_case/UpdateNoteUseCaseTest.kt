package com.survivalcoding.noteapp.domain.use_case

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.util.QueryResult
import com.survivalcoding.noteapp.mock.MockNoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class UpdateNoteUseCaseTest {

    val updateNoteUseCase = UpdateNoteUseCase(MockNoteRepository())

    @Test
    operator fun invoke() = runBlocking {
        var queryResult = updateNoteUseCase(Note(id = 0))
        assertEquals("존재 하지 않는 Note 입니다", (queryResult as QueryResult.Fail).msg)

        queryResult = updateNoteUseCase(Note(id = 1))
        assertEquals(true, queryResult is QueryResult.Success)

        queryResult = updateNoteUseCase(Note(id = 2))
        assertEquals(true, queryResult is QueryResult.Fail)
    }
}