package com.gilib.taskit.view

sealed class Screen(val route: String) {
    data object MainScreen: Screen("main_screen")
    data object NewUpdateTaskScreen: Screen("new_or_update_task_screen")
}