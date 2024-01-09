package com.example.project006.ui.fragments

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
import com.example.project006.R
import com.example.project006.databinding.FragmentAddBinding
import com.example.project006.model.Note
import com.example.project006.ui.MainActivity
import com.example.project006.viewmodel.NoteViewModel


class AddFragment : Fragment(R.layout.fragment_add),MenuProvider {

    private var addBinding:FragmentAddBinding?=null
    private val binding get() = addBinding!!

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var addview:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        addBinding=FragmentAddBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost =requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED)

        notesViewModel=(activity as MainActivity).noteViewModel
        addview=view
    }

    private fun saveNote(view: View){
        val noteTitle=binding.addNoteTitle.text.toString().trim()
        val noteDesc=binding.addNoteDesc.text.toString().trim()

        if(noteTitle.isNotEmpty()){
            val note= Note(0,noteTitle,noteDesc)
            notesViewModel.addNote(note)

            Toast.makeText(addview.context,"Notes added Successfully",Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeFragment,false)
        }else{
            Toast.makeText(addview.context,"Please Enter Note Title",Toast.LENGTH_SHORT).show()
        }



    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
       menu.clear()
        menuInflater.inflate(R.menu.add_menu,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
      return when(menuItem.itemId){
          R.id.saveMenu->{
              saveNote(addview)
              true
          }
          else->true
      }
    }

    override fun onDestroy() {
        super.onDestroy()
        addBinding=null
    }

}