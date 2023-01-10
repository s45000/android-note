package com.survivalcoding.noteapp.domain.use_case

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import com.survivalcoding.noteapp.domain.util.QueryResult
import java.util.*

class AddNoteUseCase(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(title: String, body: String, color: Int, date: Long): QueryResult {
        return try {
            noteRepository.addNote(Note(title, body, color, date))
            QueryResult.Success("Note 생성 성공")
        } catch (e: Exception) {
            QueryResult.Fail(e.message.toString())
        }
    }
}