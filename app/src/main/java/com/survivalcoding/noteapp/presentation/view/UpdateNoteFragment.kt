package com.survivalcoding.noteapp.presentation.view

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
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.FragmentUpdateNoteBinding
import com.survivalcoding.noteapp.domain.model.NoteColor
import com.survivalcoding.noteapp.presentation.viewmodel.UpdateNoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpdateNoteFragment : Fragment(R.layout.fragment_update_note) {
    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UpdateNoteViewModel by viewModels()

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.colorRadioGroup.setOnCheckedChangeListener { _, checkedId ->

            val noteColor = resIdToNoteColor(checkedId)
            binding.root.setBackgroundColor(noteColor.rgb.toInt())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.noteState.collectLatest {
                    binding.titleEditTextView.setText(it.title)
                    binding.bodyEditTextView.setText(it.body)
                    when (it.color) {
                        NoteColor.RedOrange -> binding.redOrangeRadio.isChecked = true
                        NoteColor.RedPink -> binding.redPinkRadio.isChecked = true
                        NoteColor.BabyBlue -> binding.babyBlueRadio.isChecked = true
                        NoteColor.Violet -> binding.violetRadio.isChecked = true
                        NoteColor.LightGreen -> binding.lightGreenRadio.isChecked = true
                    }
                }
            }
        }

        binding.saveNoteButton.setOnClickListener {
            viewModel.update(
                binding.titleEditTextView.text.toString(),
                binding.bodyEditTextView.text.toString(),
                resIdToNoteColor(binding.colorRadioGroup.checkedRadioButtonId).rgb
            )
            findNavController().popBackStack()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.tempSave(
            binding.titleEditTextView.text.toString(),
            binding.bodyEditTextView.text.toString(),
            resIdToNoteColor(binding.colorRadioGroup.checkedRadioButtonId).rgb
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}