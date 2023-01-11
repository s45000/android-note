package com.survivalcoding.noteapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.noteapp.Config
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.use_case.NoteUseCases
import com.survivalcoding.noteapp.domain.util.OrderType
import com.survivalcoding.noteapp.domain.util.QueryResult
import com.survivalcoding.noteapp.presentation.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel
@Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {
    private val _mainUiState: MutableStateFlow<MainUiState> by lazy {
        val queryResult = noteUseCases.getOrderedNotesUseCase(
            Config.DEFAULT_ORDER_TYPE,
            Config.DEFAULT_IS_ASCENDING
        )
        val notes = (queryResult as QueryResult.Success).value
        MutableStateFlow(
            MainUiState(
                notes,
                null,
                Config.DEFAULT_ORDER_TYPE,
                Config.DEFAULT_IS_ASCENDING
            )
        )
    }

    val mainUiState = _mainUiState.asStateFlow()

    fun load(orderType: OrderType, isAscending: Boolean) {
        val queryResult = noteUseCases.getOrderedNotesUseCase(orderType, isAscending)
        val notes = (queryResult as QueryResult.Success).value
        _mainUiState.value = mainUiState.value.copy(notes = notes)
    }

    fun delete(note: Note) {
        viewModelScope.launch {
            noteUseCases.deleteNoteUseCase(note)
            load(mainUiState.value.orderType, mainUiState.value.isAscending)
        }
    }

    fun add(note: Note) {
        viewModelScope.launch {
            noteUseCases.addNoteUseCase(note)
            //load(mainUiState.value.orderType, mainUiState.value.isAscending)
        }
    }

    fun update(note: Note) {
        viewModelScope.launch {
            noteUseCases.updateNoteUseCase(note)
            load(mainUiState.value.orderType, mainUiState.value.isAscending)
        }
    }

    fun changeOrderType(orderType: OrderType) {
        _mainUiState.value = mainUiState.value.copy(orderType = orderType)
        load(mainUiState.value.orderType, mainUiState.value.isAscending)
    }

    fun changeIsAscending(isAscending: Boolean) {
        _mainUiState.value = mainUiState.value.copy(isAscending = isAscending)
        load(mainUiState.value.orderType, mainUiState.value.isAscending)
    }


}