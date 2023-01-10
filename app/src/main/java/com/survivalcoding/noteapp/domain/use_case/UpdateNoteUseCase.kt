package com.survivalcoding.noteapp.domain.use_case

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import com.survivalcoding.noteapp.domain.util.QueryResult
import java.util.*

class UpdateNoteUseCase(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(id: Int, title: String, body: String, color: Int, date: Long): QueryResult {
        return try {
            noteRepository.deleteNote(id)
            noteRepository.addNote(Note(title, body, color, date))
            QueryResult.Success("Note 변경 성공")
        } catch (e: Exception) {
            QueryResult.Fail(e.message.toString())
        }
    }
}