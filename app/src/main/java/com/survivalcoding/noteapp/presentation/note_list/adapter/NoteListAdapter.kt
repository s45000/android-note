package com.survivalcoding.noteapp.presentation.note_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.NoteItemBinding
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.NoteColor

class NoteListAdapter(
    private val noteDeleteCallback: (Note) -> Unit,
    private val noteSelectCallback: (Note) -> Unit,
) :
    ListAdapter<Note, NoteListAdapter.ViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

    class ViewHolder(val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)

        return ViewHolder(NoteItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.title.text = getItem(position).title
        holder.binding.body.text = getItem(position).body

        val resources = holder.binding.root.resources
        val drawable =
            when (getItem(position).color) {
                NoteColor.RedOrange -> R.drawable.item_background_red_orange
                NoteColor.RedPink -> R.drawable.item_background_red_pink
                NoteColor.BabyBlue -> R.drawable.item_background_baby_blue
                NoteColor.Violet -> R.drawable.item_background_violet
                NoteColor.LightGreen -> R.drawable.item_background_light_green
            }

        val note = getItem(position)
        holder.binding.itemBackground.background =
            ResourcesCompat.getDrawable(resources, drawable, null)

        holder.binding.deleteButton.setOnClickListener {
            noteDeleteCallback(note)
        }

        holder.binding.root.setOnClickListener {
            noteSelectCallback(note)
        }
    }
}