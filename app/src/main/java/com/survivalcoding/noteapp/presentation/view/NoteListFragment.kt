package com.survivalcoding.noteapp.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.FragmentNoteListBinding
import com.survivalcoding.noteapp.presentation.adapter.NoteListAdapter
import com.survivalcoding.noteapp.presentation.viewmodel.NoteListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoteListFragment : Fragment(R.layout.fragment_note_list) {

    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NoteListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)


        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val noteListAdapter = NoteListAdapter()
        val noteListRecyclerView = binding.noteListRecyclerview

        noteListRecyclerView.layoutManager = LinearLayoutManager(view.context)
        noteListRecyclerView.adapter = noteListAdapter
        noteListRecyclerView.setHasFixedSize(true)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.noteListUiState.collectLatest {
                    Log.d("notes", it.notes[0].toString())
                    noteListAdapter.submitList(it.notes.toMutableList())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}