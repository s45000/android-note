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
            val count = noteRepository.updateNote(note)
            if (count == 1)
                QueryResult.Success("Note 변경 성공")
            else
                QueryResult.Fail("존재 하지 않는 Note 입니다")
        } catch (e: Exception) {
            QueryResult.Fail(e.message.toString())
        }
    }
}