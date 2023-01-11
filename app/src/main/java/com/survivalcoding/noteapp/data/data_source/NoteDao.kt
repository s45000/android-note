package com.survivalcoding.noteapp.data.data_source

import androidx.room.*
import com.survivalcoding.noteapp.domain.model.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {
    @Query("SELECT * FROM NOTE WHERE id = :id")
    suspend fun getNote(id: Int): Note

    @Insert
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM NOTE")
    fun getNotes(): Flow<List<Note>>
}