package com.gilib.taskit.model

import android.content.Context
import androidx.room.Room

object Graph {
    private lateinit var database: TaskDatabase

    val tasksRepository by lazy {
        OfflineTasksRepository(database.taskDao())
    }

    fun provide(context: Context) {
        database = Room.databaseBuilder(context, TaskDatabase::class.java, "task.db").build()
    }
}