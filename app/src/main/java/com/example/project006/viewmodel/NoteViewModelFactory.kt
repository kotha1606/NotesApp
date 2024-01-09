package com.example.project006.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.project006.repository.NoteRepository

class NoteViewModelFactory(val app:Application,private val noteRepository: NoteRepository):ViewModelProvider.Factory{

    @Override
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(app,noteRepository) as T
    }

}