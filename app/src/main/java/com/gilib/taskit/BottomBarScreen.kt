package com.gilib.taskit

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

sealed class BottomBarScreen(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    object Starred : BottomBarScreen(
        title = "Starred",
        selectedIcon = Icons.Filled.Star,
        unselectedIcon = Icons.Outlined.Star
    )

    object Tasks: BottomBarScreen(
        title = "My Tasks",
        selectedIcon = Icons.Filled.DateRange,
        unselectedIcon = Icons.Outlined.DateRange
    )
}

//@Composable
//fun HomeScreen() {
//    var selectedIndex by remember {
//        mutableStateOf(0)
//    }
//
//    val items = listOf(
//        BottomBarScreen.Starred,
//        BottomBarScreen.Tasks
//    )
//
//    Scaffold (
//        bottomBar = {
//            NavigationBar (
//                containerColor = Color.White
//            ) {
//                items.forEachIndexed { index, item ->
//                    NavigationBarItem(
//                        selected = (index == selectedIndex),
//                        onClick = {
//                            selectedIndex = index
//                        },
//                        label = {
//                            Text(text = item.title)
//                        },
//                        colors = NavigationBarItemDefaults.colors(
//                            selectedIconColor = Color.Blue,
//                            selectedTextColor = Color.Blue
//                        ),
//                        icon = {
//                            Icon(
//                                imageVector = (
//                                        if (index == selectedIndex)
//                                            item.selectedIcon
//                                        else
//                                            item.unselectedIcon
//                                        ),
//                                contentDescription = item.title )
//                        }
//                    )
//                }
//            }
//        }
//    ) {
//        it.calculateBottomPadding()
//        val tasksManager = TasksManager(LocalContext.current)
//        val navController = rememberNavController()
//        MainScreen(tasksManager , navController)
//    }
//}
//
//@Preview( showBackground = true )
//@Composable
//private fun Preview() {
//    HomeScreen()
//}
