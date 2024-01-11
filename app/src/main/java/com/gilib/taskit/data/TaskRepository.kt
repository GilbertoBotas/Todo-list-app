package com.gilib.taskit.data

import androidx.lifecycle.LiveData

class TaskRepository(private val taskDao: TaskDao) {

    val readAllData: LiveData<List<Task>> = taskDao.readAllTasks()

    suspend fun addTask(task: Task) {
        taskDao.addTask(task)
    }
}