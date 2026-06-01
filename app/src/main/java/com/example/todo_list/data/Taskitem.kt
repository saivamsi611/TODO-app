package com.example.todo_list.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Taskitem(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    var name: String,
    var isDone: Boolean=false
)
