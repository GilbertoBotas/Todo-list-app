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
    fun getTask(id: Long): Flow<Task>

    @Query("SELECT * FROM task WHERE starred = 1 AND completed = 0 ORDER BY id DESC")
    fun getNewestStarredTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE starred = 1 AND completed = 0 ORDER BY id ASC")
    fun getOldestStarredTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE starred = 1 AND completed = 0 ORDER BY title ASC")
    fun getAZStarredTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE starred = 1 AND completed = 0 ORDER BY title DESC")
    fun getZAStarredTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE completed = 0 ORDER BY id DESC")
    fun getNewestTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE completed = 0 ORDER BY id ASC")
    fun getOldestTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE completed = 0 ORDER BY title ASC")
    fun getAZTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE completed = 0 ORDER BY title DESC")
    fun getZATasks(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE completed = 1 ORDER BY id DESC")
    fun getCompletedTasks(): Flow<List<Task>>
}