package com.survivalcoding.noteapp.domain.use_case

import com.survivalcoding.noteapp.mock.MockNoteRepository
import org.junit.Test

class NoteUseCasesTest {

    private val mockNoteRepository = MockNoteRepository()
    val noteUseCase = NoteUseCases(
        addNoteUseCase = AddNoteUseCase(mockNoteRepository),
        getNoteUseCase = GetNoteUseCase(mockNoteRepository),
        deleteNoteUseCase = DeleteNoteUseCase(mockNoteRepository),
        updateNoteUseCase = UpdateNoteUseCase(mockNoteRepository),
        getOrderedNotesUseCase = GetOrderedNotesUseCase(mockNoteRepository)
    )

    @Test
    fun test() {

    }
}