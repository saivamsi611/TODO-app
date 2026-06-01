package com.example.todo_list.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TaskviewmodelFactory( private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(viewmodel::class.java)) {
            viewmodel(
                application =application
            ) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
    }

