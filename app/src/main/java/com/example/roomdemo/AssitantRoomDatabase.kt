package com.example.roomdemo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(Assitant::class)], version = 1)


 abstract class AssitantRoomDatabase:RoomDatabase() {

     abstract  fun assitantDao():AssitantDao

     companion object{

         private var INSTANCE: AssitantRoomDatabase?=null

         fun getInstance(context: Context):AssitantRoomDatabase{
             synchronized(this){
                 var instance = INSTANCE

                 if(instance==null){
                     instance = Room.databaseBuilder(
                         context.applicationContext,
                         AssitantRoomDatabase::class.java,
                         "assitant_database"
                     ).fallbackToDestructiveMigration().build()

                     INSTANCE = instance
                 }
                 return instance
             }
         }
     }
}