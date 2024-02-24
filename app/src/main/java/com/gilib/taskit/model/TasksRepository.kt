package com.gilib.taskit.model

import kotlinx.coroutines.flow.Flow

class TasksRepository(private val taskDao: TaskDao) : TaskDao {
    override suspend fun addTask(task: Task) = taskDao.addTask(task)

    override suspend fun updateTask(task: Task) = taskDao.updateTask(task)

    override suspend fun deleteAllTasks() = taskDao.deleteAllTasks()

    override fun getTask(id: Long): Flow<Task> = taskDao.getTask(id)
    override fun getNewestStarredTasks(): Flow<List<Task>> = taskDao.getNewestStarredTasks()

    override fun getOldestStarredTasks(): Flow<List<Task>> = taskDao.getOldestStarredTasks()

    override fun getAZStarredTasks(): Flow<List<Task>> = taskDao.getAZStarredTasks()

    override fun getZAStarredTasks(): Flow<List<Task>> = taskDao.getZAStarredTasks()

    override fun getNewestTasks(): Flow<List<Task>> = taskDao.getNewestTasks()

    override fun getOldestTasks(): Flow<List<Task>> = taskDao.getOldestTasks()

    override fun getAZTasks(): Flow<List<Task>> = taskDao.getAZTasks()

    override fun getZATasks(): Flow<List<Task>> = taskDao.getZATasks()

    override fun getCompletedTasks(): Flow<List<Task>> = taskDao.getCompletedTasks()

}