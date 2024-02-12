package com.gilib.taskit.model

import kotlinx.coroutines.flow.Flow

class OfflineTasksRepository(private val taskDao: TaskDao) : TasksRepository {
    override suspend fun addTask(task: Task) = taskDao.addTask(task)

    override suspend fun updateTask(task: Task) = taskDao.updateTask(task)

    override suspend fun deleteAllTasks() = taskDao.deleteAllTasks()

    override fun getTask(id: Long): Flow<Task> = taskDao.getItem(id)

    override fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    override fun getStarredTasks(): Flow<List<Task>> = taskDao.getStarredTasks()

    override fun getCompletedTasks(): Flow<List<Task>> = taskDao.getCompletedTasks()

}