package com.survivalcoding.noteapp.domain.use_case

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import com.survivalcoding.noteapp.domain.util.QueryResult

class AddNoteUseCase(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(note: Note): QueryResult {
        return try {
            noteRepository.addNote(note)
            QueryResult.Success("Note 생성 성공")
        } catch (e: Exception) {
            QueryResult.Fail(e.message.toString())
        }
    }
}