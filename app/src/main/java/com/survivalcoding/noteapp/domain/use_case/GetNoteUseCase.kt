package com.survivalcoding.noteapp.domain.use_case

import com.survivalcoding.noteapp.domain.repository.NoteRepository
import com.survivalcoding.noteapp.domain.util.QueryResult

class GetNoteUseCase(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(id: Int): QueryResult {
        return try {
            noteRepository.getNote(id)
            QueryResult.Success("Note 획득 성공") //TODO 반환값
        } catch (e: Exception) {
            QueryResult.Fail(e.message.toString())
        }
    }
}