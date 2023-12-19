package com.example.myapplication

sealed class Screen(val route: String) {
    object MainScreen: Screen("main_screen")
    object NewTaskScreen: Screen("new_task_screen")
    object UpdateTaskScreen: Screen("update_task_screen")
}