package com.survivalcoding.noteapp.domain.use_case

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import com.survivalcoding.noteapp.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetOrderedNotesUseCase(
    private val noteRepository: NoteRepository
) {
//    operator fun invoke(orderType: OrderType, isAscending: Boolean): Flow<List<Note>> {
//        return try {
//            noteRepository.getNotes().map {
//                when (orderType){
//                     ->
//                }
//
//
//            }
//
//        } catch (e: Exception) {
//
//        }
//    }
}