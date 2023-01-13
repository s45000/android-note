package com.survivalcoding.noteapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val title: String = "",
    val body: String = "",
    val color: NoteColor = NoteColor.BabyBlue,
    val date: Long = 0,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)