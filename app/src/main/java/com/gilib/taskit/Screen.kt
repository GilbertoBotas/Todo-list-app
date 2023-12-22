package com.gilib.taskit

sealed class Screen(val route: String) {
    object MainScreen: Screen("main_screen")
    object NewUpdateTaskScreen: Screen("new_or_update_task_screen")
}