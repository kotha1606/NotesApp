package com.example.project006.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.project006.databinding.NoteLayoutBinding
import com.example.project006.model.Note
import com.example.project006.ui.fragments.HomeFragmentDirections

class NoteAdapter:RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(val binding: NoteLayoutBinding):RecyclerView.ViewHolder(binding.root)

    private val differCallback=object: DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id==newItem.id &&
                    oldItem.noteDesc==newItem.noteDesc &&
                    oldItem.noteTitle==newItem.noteTitle
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem==newItem
        }
    }
    val differ= AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            NoteLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote=differ.currentList[position]
        holder.binding.noteTitle.text=currentNote.noteTitle
        holder.binding.noteDesc.text=currentNote.noteDesc
        holder.itemView.setOnClickListener{
            val direction=HomeFragmentDirections.actionHomeFragmentToEditFragment(currentNote)
            it.findNavController().navigate(direction)
        }

    }
}