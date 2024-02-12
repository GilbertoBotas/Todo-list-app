package com.gilib.taskit.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Query("DELETE FROM task WHERE completed = 1")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM task WHERE id = :id ")
    fun getItem(id: Long): Flow<Task>

    @Query("SELECT * FROM task WHERE completed = 0 ORDER BY starred DESC")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE starred = 1 AND completed = 0")
    fun getStarredTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE completed = 1")
    fun getCompletedTasks(): Flow<List<Task>>
}