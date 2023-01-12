package com.survivalcoding.noteapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.presentation.viewmodel.UpdateNoteViewModel

class UpdateNoteFragment : Fragment() {

    companion object {
        fun newInstance() = UpdateNoteFragment()
    }

    private lateinit var viewModel: UpdateNoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_update_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UpdateNoteViewModel::class.java)
        // TODO: Use the ViewModel
    }

}