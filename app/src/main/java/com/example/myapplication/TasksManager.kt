package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class TasksManager(context: Context) {
    private val sharedPreferences: SharedPreferences
    private val gson: Gson
    var tasks: MutableList<Task>

    init {
        sharedPreferences = context.getSharedPreferences("tasks_pref", Context.MODE_PRIVATE)
        gson = Gson()
        tasks = getListTasks()
    }


    fun saveTasks() {
        val tasksJson = gson.toJson(tasks)
        sharedPreferences.edit().putString("tasks", tasksJson).apply()
    }

    fun getListTasks(): MutableList<Task> {
        val tasksJson = sharedPreferences.getString("tasks", "")
        val type: Type = object : TypeToken<MutableList<Task>>() {}.type
        return gson.fromJson(tasksJson, type) ?: mutableListOf()
    }
}
