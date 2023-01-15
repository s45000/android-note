package com.survivalcoding.noteapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.FragmentAddNoteBinding
import com.survivalcoding.noteapp.domain.model.NoteColor
import com.survivalcoding.noteapp.presentation.view.nav.NoteNav
import com.survivalcoding.noteapp.presentation.viewmodel.AddNoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteFragment : Fragment(R.layout.fragment_add_note) {
    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddNoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val resIdToNoteColor: (Int) -> NoteColor = {
            when (it) {
                R.id.baby_blue_radio -> NoteColor.BabyBlue
                R.id.light_green_radio -> NoteColor.LightGreen
                R.id.violet_radio -> NoteColor.Violet
                R.id.red_orange_radio -> NoteColor.RedOrange
                R.id.red_pink_radio -> NoteColor.RedPink
                else -> NoteColor.BabyBlue
            }
        }

        binding.colorRadioGroup.setOnCheckedChangeListener { _, checkedId ->

            val noteColor = resIdToNoteColor(checkedId)
            binding.root.setBackgroundColor(noteColor.rgb.toInt())
        }

        binding.saveNoteButton.setOnClickListener {
            viewModel.add(
                binding.titleEditTextView.text.toString(),
                binding.bodyEditTextView.text.toString(),
                resIdToNoteColor(binding.colorRadioGroup.checkedRadioButtonId).rgb
            )

            findNavController().navigate(NoteNav.NoteList.route)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}