package com.example.todo_list.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_list.data.TaskDatabase
import com.example.todo_list.data.Taskitem
import com.example.todo_list.repository.repository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class viewmodel(application: Application): AndroidViewModel(application){

    private  val dao= TaskDatabase.getDatabase(application).DAO()
    private val repository= repository(dao)
    val  viewalltasks: StateFlow<List<Taskitem>> = repository.getalltasks().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    fun addTask(Taskitem: Taskitem){
        viewModelScope.launch{
            repository.insert(Taskitem)
        }

    }
    fun updateTask(Taskitem: Taskitem){
        viewModelScope.launch{
            repository.update(Taskitem)
        }
    }
    fun deleteTask(Taskitem: Taskitem){
        viewModelScope.launch{
            repository.delete(Taskitem)
        }

    }
        // Coroutine code
    }




