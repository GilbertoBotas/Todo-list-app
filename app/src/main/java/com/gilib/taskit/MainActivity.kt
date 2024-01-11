package com.gilib.taskit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            val viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
            val tasksManager = TasksManager(this)
            val navigator = Navigator(tasksManager)
            navigator.Navigation()
        }
    }
}