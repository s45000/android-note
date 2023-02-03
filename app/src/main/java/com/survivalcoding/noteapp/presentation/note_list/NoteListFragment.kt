package com.survivalcoding.noteapp.presentation.note_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.FragmentNoteListBinding
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.util.OrderType
import com.survivalcoding.noteapp.presentation.note_list.adapter.NoteListAdapter
import com.survivalcoding.noteapp.presentation.util.nav.NoteNav
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
    ): View {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val noteListAdapter =
            NoteListAdapter(
                noteDeleteCallback = { note: Note -> viewModel.delete(note) },
                noteSelectCallback = { note: Note ->
                    findNavController()
                        .navigate(NoteNav.NoteUpdate.route + "?noteId=${note.id}")
                }
            )

        val noteListRecyclerView = binding.noteListRecyclerview

        noteListRecyclerView.layoutManager = LinearLayoutManager(view.context)
        noteListRecyclerView.adapter = noteListAdapter
        noteListRecyclerView.setHasFixedSize(true)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.noteListUiState.collectLatest {
                    noteListAdapter.submitList(it.notes)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.menuToggleUiState.collectLatest {
                    if (it) {
                        binding.settingLayout.transitionToEnd()
                    } else {
                        binding.settingLayout.transitionToStart()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.snackBarEvent.collectLatest {
                    Snackbar.make(
                        binding.root,
                        it,
                        Snackbar.LENGTH_SHORT
                    ).setAction("Undo") {
                        viewModel.undo()
                    }.show()
                }
            }
        }


        binding.menuButton.setOnClickListener {
            viewModel.menuSelectToggle()
        }
        binding.orderTypeGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.title_radio_button -> viewModel.changeOrderType(OrderType.TITLE)
                R.id.date_radio_button -> viewModel.changeOrderType(OrderType.DATE)
                R.id.color_radio_button -> viewModel.changeOrderType(OrderType.COLOR)
            }
        }
        binding.isAscendingGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.asc_radio_button -> viewModel.changeIsAscending(true)
                R.id.desc_radio_button -> viewModel.changeIsAscending(false)
            }
        }
        binding.noteAddButton.setOnClickListener {
            findNavController().navigate(NoteNav.NoteAdd.route)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.load(
            viewModel.noteListUiState.value.orderType,
            viewModel.noteListUiState.value.isAscending
        )
    }

}