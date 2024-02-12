package com.gilib.taskit.model

import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    suspend fun addTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun deleteAllTasks()

    fun getTask(id: Long): Flow<Task>

    fun getAllTasks(): Flow<List<Task>>

    fun getStarredTasks(): Flow<List<Task>>

    fun getCompletedTasks(): Flow<List<Task>>
}