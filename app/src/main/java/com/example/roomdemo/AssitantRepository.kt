package com.example.roomdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class AssitantRepository(private val assitantDao: AssitantDao) {

    val allAssitant:LiveData<List<Assitant>> = assitantDao.getAllAssitants()
    val searchResults = MutableLiveData<List<Assitant>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertAssitant(newassitant:Assitant){
        coroutineScope.launch(Dispatchers.IO) {
            assitantDao.insertAssitant(newassitant)
        }
    }

    fun deleteAssitant(name:String){
        coroutineScope.launch (Dispatchers.IO){
            assitantDao.deleteAssitant(name)
        }
    }
/*
    fun findAssitant(name: String){
        coroutineScope.launch(Dispatchers.Main){
            searchResults.value=asyncFind(name).await()
        }
    }

    private fun asyncFind(name:String):Deferred<List<Assitant>?>=
        coroutineScope.async(Dispatchers.IO){
        return@async assitantDao.findAssitant(name)
        }

 */
}