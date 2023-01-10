package com.survivalcoding.noteapp.domain.use_case

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import com.survivalcoding.noteapp.domain.util.OrderType
import com.survivalcoding.noteapp.domain.util.OrderType.TITLE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetOrderedNotesUseCase(
    private val noteRepository: NoteRepository
) {
    operator fun invoke(orderType: OrderType, isAscending: Boolean): Flow<List<Note>> {

        return noteRepository.getNotes().map { notes ->
            when (orderType) {
                TITLE -> if (isAscending) notes.sortedBy { it.title } else notes.sortedByDescending { it.title }
                OrderType.DATE -> if (isAscending) notes.sortedBy { it.date } else notes.sortedByDescending { it.date }
                OrderType.COLOR -> if (isAscending) notes.sortedBy { it.color } else notes.sortedByDescending { it.color }
            }
        }

    }
}