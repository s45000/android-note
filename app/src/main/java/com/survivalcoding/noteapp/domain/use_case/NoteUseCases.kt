package com.survivalcoding.noteapp.domain.use_case

data class NoteUseCases(
    val addNoteUseCase: AddNoteUseCase,
    val getNoteUseCase: GetNoteUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val updateNoteUseCase: UpdateNoteUseCase,
    val getOrderedNotesUseCase: GetOrderedNotesUseCase
)