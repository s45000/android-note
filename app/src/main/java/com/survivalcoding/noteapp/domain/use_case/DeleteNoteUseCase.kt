package com.survivalcoding.noteapp.domain.use_case

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import com.survivalcoding.noteapp.domain.util.QueryResult

class DeleteNoteUseCase(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(note: Note): QueryResult {
        return try {
            val count = noteRepository.deleteNote(note)
            if (count == 1)
                QueryResult.Success("Note 삭제 성공")
            else
                QueryResult.Fail("존재하지 않는 Note")
        } catch (e: Exception) {
            QueryResult.Fail(e.message.toString())
        }
    }
}