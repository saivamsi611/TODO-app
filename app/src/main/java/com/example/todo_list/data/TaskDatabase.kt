package com.example.todo_list.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Taskitem::class], version = 1)
abstract  class TaskDatabase : RoomDatabase(){
    abstract  fun DAO (): TaskDAO
    companion object{
        @Volatile
        private var Instance: TaskDatabase?=null
        fun getDatabase(context: Context): TaskDatabase{
            return Instance?:synchronized(lock = this){
                Room.databaseBuilder(context,
                    TaskDatabase::class.java,
                    "task_database").
                fallbackToDestructiveMigration().
                build()
            }
        }
    }
}