package com.example.roomdemo

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(application: Application): ViewModel() {

    var allAssitant: LiveData<List<Assitant>>
    private val repository: AssitantRepository
    val searchResults: MutableLiveData<List<Assitant>>

    init {
        val assitantDb = AssitantRoomDatabase.getInstance(application)
        val assitantDao = assitantDb.assitantDao()
        repository = AssitantRepository(assitantDao)

        allAssitant = repository.allAssitant
        searchResults = repository.searchResults

    }

    fun insertAssitant(assitant: Assitant){
        repository.insertAssitant(assitant)
    }
    /*

    fun findAssitant(name:String){
        repository.findAssitant(name)
    }

     */

    fun deleteAssitant(name:String){
        repository.deleteAssitant(name)
    }
}