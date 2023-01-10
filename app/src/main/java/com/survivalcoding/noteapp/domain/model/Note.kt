package com.survivalcoding.noteapp.domain.model

import java.util.Date

data class Note(
    val title: String,
    val body: String,
    val color: Int,
    val date: Date,
    // Auto Increase
    val id: Int = 0,
)