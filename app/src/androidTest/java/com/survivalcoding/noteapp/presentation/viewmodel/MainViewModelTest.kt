package com.survivalcoding.noteapp.presentation.viewmodel

import com.survivalcoding.noteapp.data.data_source.NoteDatabase
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.use_case.NoteUseCases
import com.survivalcoding.noteapp.domain.util.OrderType
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.robolectric.annotation.Config
import javax.inject.Inject

@HiltAndroidTest
@Config(application = HiltTestApplication::class)
class MainViewModelTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var noteDatabase: NoteDatabase

    @Inject
    lateinit var notesUseCase: NoteUseCases
    lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        mainViewModel = MainViewModel(notesUseCase)
    }

    @After
    fun tearDown() {
        noteDatabase.close()
    }

    @Test
    fun load() = runBlocking {
        val dummyList: MutableList<Note> = ArrayList()
        for (i in 1..10) {
            val noteDummy = Note(
                "title $i",
                "body $i",
                i,
                i.toLong() * 50,
                id = i
            )
            dummyList.add(noteDummy)
            mainViewModel.add(noteDummy)
        }

        mainViewModel.load(OrderType.TITLE, true)
        delay(3000)

        dummyList.sortBy { it.title }
        Assert.assertEquals(dummyList, mainViewModel.mainUiState.value.notes.first())
    }

    @Test
    fun delete() {
    }

    @Test
    fun add() {
    }

    @Test
    fun update() {
    }

    @Test
    fun changeOrderType() {
    }

    @Test
    fun changeIsAscending() {
    }
}