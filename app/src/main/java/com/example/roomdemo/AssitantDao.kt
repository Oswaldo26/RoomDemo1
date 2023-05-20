package com.example.roomdemo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface AssitantDao {

    @Insert
    fun insertAssitant(assitant: Assitant)

    @Query("SELECT*FROM assitants WHERE assitantName =:name")
    fun findAssitant(name:String):List<Assitant>

    @Query("DELETE FROM assitants WHERE assitantName =:name")
    fun deleteAssitant(name:String)

    @Query("SELECT*FROM assitants ")
    fun getAllAssitants():LiveData<List<Assitant>>

}