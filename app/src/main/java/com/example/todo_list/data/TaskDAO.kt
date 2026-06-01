package com.example.todo_list.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDAO {
    @Insert
    suspend fun insert(Taskitem: Taskitem)

    @Update
    suspend fun update(Taskitem: Taskitem)
    @Delete
    suspend fun delete(Taskitem: Taskitem)
    @Query("select * from task order by  id asc")
     fun getalltasks(): Flow<List<Taskitem>>
}