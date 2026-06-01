package com.example.todo_list.repository

import com.example.todo_list.data.TaskDAO
import com.example.todo_list.data.Taskitem
import kotlinx.coroutines.flow.Flow

class repository(private val dao: TaskDAO) {
     fun getalltasks(): Flow<List<Taskitem>> {
          return dao.getalltasks()}
          suspend fun insert(Taskitem: Taskitem){
          dao.insert(Taskitem)}
          suspend fun update(Taskitem: Taskitem){
          dao.update(Taskitem)}
          suspend fun delete(Taskitem: Taskitem){
          dao.delete(Taskitem)}

     }



