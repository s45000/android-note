package com.survivalcoding.noteapp.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.survivalcoding.noteapp.domain.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}