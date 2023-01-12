package com.survivalcoding.noteapp.domain.use_case

import com.survivalcoding.noteapp.domain.util.QueryResult
import com.survivalcoding.noteapp.mock.MockNoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetNoteUseCaseTest {

    val getNoteUseCase = GetNoteUseCase(MockNoteRepository())

    @Test
    operator fun invoke() = runBlocking {
        var queryResult = getNoteUseCase(-1)
        assertEquals(true, queryResult is QueryResult.Fail)

        queryResult = getNoteUseCase(0)
        assertEquals("해당 id 의 노트가 없습니다.", (queryResult as QueryResult.Fail).msg)

        queryResult = getNoteUseCase(1)
        assertEquals(true, queryResult is QueryResult.Success)
    }
}