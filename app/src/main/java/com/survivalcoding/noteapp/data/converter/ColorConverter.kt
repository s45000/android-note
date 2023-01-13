package com.survivalcoding.noteapp.data.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.survivalcoding.noteapp.domain.model.NoteColor

@ProvidedTypeConverter
class ColorConverter {
    @TypeConverter
    fun longToNoteColor(value: Long?): NoteColor? {
        return NoteColor.values().firstOrNull { it.rgb == value }
    }

    @TypeConverter
    fun noteColorToLong(value: NoteColor?): Long? {
        return value?.rgb
    }
}