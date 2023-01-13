package com.survivalcoding.noteapp.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.survivalcoding.noteapp.data.converter.ColorConverter
import com.survivalcoding.noteapp.domain.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(ColorConverter::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}