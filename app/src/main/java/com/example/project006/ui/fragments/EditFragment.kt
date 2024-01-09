package com.example.project006.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.project006.R
import com.example.project006.databinding.FragmentEditBinding
import com.example.project006.model.Note
import com.example.project006.ui.MainActivity
import com.example.project006.viewmodel.NoteViewModel


class EditFragment : Fragment(R.layout.fragment_edit),MenuProvider {

    private var editBinding:FragmentEditBinding?=null
    private val binding get()= editBinding!!

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var currentNote:Note

    val args: EditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        editBinding=FragmentEditBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost =requireActivity()

        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED)
        notesViewModel=(activity as MainActivity).noteViewModel
        currentNote=args.note!!

        binding.editNoteTitle.setText(currentNote.noteTitle)
        binding.editNoteDesc.setText(currentNote.noteDesc)

        binding.editNoteFab.setOnClickListener {
            val noteTitle = binding.editNoteTitle.text.toString().trim()
            val noteDesc = binding.editNoteDesc.text.toString().trim()

            if(noteTitle.isNotEmpty()){
                val note=Note(currentNote.id,noteTitle,noteDesc)
                notesViewModel.updateNote(note)
                view.findNavController().popBackStack(R.id.homeFragment,false)
            }else{
                Toast.makeText(context,"Please Enter the title",Toast.LENGTH_SHORT).show()
            }

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        editBinding=null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.edit_menu,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when(menuItem.itemId){
                R.id.deleteMenu->{
                    deleteNote()
                    true
                }
                else->false
            }
    }
    fun deleteNote(){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete")
            setMessage("Are you sure want to delete?")
            setPositiveButton("Delete"){_,_->
                notesViewModel.delteNote(currentNote)
                Toast.makeText(context,"Note has been Deleted",Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment,false) }
            setNegativeButton("Cancel",null)
        }.create().show()
    }

}