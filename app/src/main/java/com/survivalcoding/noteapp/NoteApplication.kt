package com.survivalcoding.noteapp

import android.app.Application
import com.survivalcoding.noteapp.data.data_source.NoteDatabase
import com.survivalcoding.noteapp.data.repository.NoteRepositoryImpl
import com.survivalcoding.noteapp.domain.use_case.*
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NoteApplication : Application()