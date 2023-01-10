package com.survivalcoding.noteapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Note(
    val title: String,
    val body: String,
    val color: Int,
    val date: Date,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)