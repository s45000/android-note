package com.survivalcoding.noteapp.domain.use_case

import com.survivalcoding.noteapp.domain.repository.NoteRepository
import com.survivalcoding.noteapp.domain.util.OrderType
import com.survivalcoding.noteapp.domain.util.QueryResult
import kotlinx.coroutines.flow.map

class GetOrderedNotesUseCase(
    private val noteRepository: NoteRepository
) {
    operator fun invoke(
        orderType: OrderType,
        isAscending: Boolean
    ): QueryResult {
        try {
            val notes = noteRepository.getNotes().map { notes ->
                when (orderType) {
                    OrderType.TITLE -> {
                        if (isAscending) {
                            notes.sortedBy { it.title }
                        } else {
                            notes.sortedByDescending { it.title }
                        }
                    }
                    OrderType.DATE -> {
                        if (isAscending) {
                            notes.sortedBy { it.date }
                        } else {
                            notes.sortedByDescending { it.date }
                        }
                    }
                    OrderType.COLOR -> {
                        if (isAscending) {
                            notes.sortedBy { it.color }
                        } else {
                            notes.sortedByDescending { it.color }
                        }
                    }
                }
            }
            return QueryResult.Success(notes)
        } catch (e: Exception) {
            return QueryResult.Fail(e.message.toString())
        }


    }
}