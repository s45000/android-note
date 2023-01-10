package com.survivalcoding.noteapp.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.survivalcoding.noteapp.domain.model.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {
    @Query("SELECT * FROM NOTE WHERE id = :id")
    suspend fun getNote(id: Int): Note

    @Insert()
    suspend fun addNote(note: Note)

    @Query("DELETE FROM NOTE WHERE id = :id")
    suspend fun deleteNote(id: Int)

    @Query("SELECT * FROM NOTE")
    fun getNotes(): Flow<List<Note>>
}