package com.survivalcoding.noteapp.domain.use_case

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import com.survivalcoding.noteapp.domain.util.QueryResult

class UpdateNoteUseCase(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(
        note: Note
    ): QueryResult {
        return try {
            noteRepository.updateNote(note)
            QueryResult.Success("Note 변경 성공")
        } catch (e: Exception) {
            QueryResult.Fail(e.message.toString())
        }
    }
}