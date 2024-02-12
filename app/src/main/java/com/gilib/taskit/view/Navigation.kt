package com.gilib.taskit.view

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gilib.taskit.viewModel.TaskViewModel

@Composable
fun Navigation(
    viewModel: TaskViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(route = Screen.MainScreen.route) {
            HomeView(navController = navController, viewModel = viewModel)
        }
        composable(
            route = Screen.NewUpdateTaskScreen.route + "/{taskId}/{starred}",
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.LongType
                },
                navArgument("starred") {
                    type = NavType.BoolType
                }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getLong("taskId") ?: 0L
            val starred = backStackEntry.arguments?.getBoolean("starred") ?: false
            NewUpdateTaskScreen(navController = navController, viewModel = viewModel, id = taskId, starred = starred)

        }
    }
}