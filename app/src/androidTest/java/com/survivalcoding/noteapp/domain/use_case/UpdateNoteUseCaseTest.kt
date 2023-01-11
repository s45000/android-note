package com.survivalcoding.noteapp.domain.use_case

import android.graphics.Color
import com.survivalcoding.noteapp.data.data_source.NoteDatabase
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import com.survivalcoding.noteapp.domain.util.QueryResult
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.robolectric.annotation.Config
import javax.inject.Inject

@HiltAndroidTest
@Config(application = HiltTestApplication::class)
class UpdateNoteUseCaseTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var noteDatabase: NoteDatabase

    @Inject
    lateinit var noteRepository: NoteRepository

    private lateinit var updateNoteUseCase: UpdateNoteUseCase

    private val noteDummy1 = Note(
        "title1",
        "body1",
        Color.RED,
        System.currentTimeMillis(),
        id = 1
    )

    @Before
    fun setUp() {
        hiltRule.inject()
        updateNoteUseCase = UpdateNoteUseCase(noteRepository)
    }

    @After
    fun tearDown() {
        noteDatabase.close()
    }

    @Test
    operator fun invoke() = runBlocking {
        var queryResult = updateNoteUseCase(noteDummy1)
        assertEquals(true, queryResult is QueryResult.Fail)

        noteRepository.addNote(noteDummy1)
        queryResult = updateNoteUseCase(noteDummy1)
        assertEquals(true, queryResult is QueryResult.Success)

        val noteDummy2 = noteDummy1.copy(
            title = "changeTile",
            body = "changeBody",
            color = Color.BLUE,
            date = System.currentTimeMillis()
        )
        updateNoteUseCase(noteDummy2)
        assertEquals(noteDummy2, noteRepository.getNote(noteDummy1.id))
    }
}