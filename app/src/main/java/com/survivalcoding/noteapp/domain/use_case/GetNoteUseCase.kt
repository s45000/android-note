package com.survivalcoding.noteapp.domain.use_case

import com.survivalcoding.noteapp.domain.repository.NoteRepository
import com.survivalcoding.noteapp.domain.util.QueryResult

class GetNoteUseCase(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(id: Int): QueryResult {
        return try {
            val note = noteRepository.getNote(id)
            if (note != null)
                QueryResult.Success(note)
            else
                QueryResult.Fail("해당 id 의 노트가 없습니다.")
        } catch (e: Exception) {
            QueryResult.Fail(e.message.toString())
        }
    }
}