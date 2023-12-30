package com.gilib.taskit

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class Navigator(private val tasksManager: TasksManager) {

    @Composable
    fun Navigation() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
            composable(route = Screen.MainScreen.route) {
                MainScreen(tasksManager, navController)
            }
            composable(
                route = Screen.NewUpdateTaskScreen.route + "/{taskId}",
                arguments = listOf(
                    navArgument("taskId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                backStackEntry.arguments?.getInt("taskId")?.let {
                    NewUpdateTaskScreen(tasksManager, navController, it)
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(tasksManager: TasksManager, navController: NavHostController) {
    val context = LocalContext.current
    var expanded by remember {
        mutableStateOf(false)
    }
    var sortQuery by remember {
        mutableStateOf("")
    }
    val tasks = tasksManager.tasks.toMutableStateList()

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp, 16.dp, 16.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Tasks",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                modifier = Modifier
                    .align(Alignment.Start)
                    .clip(RoundedCornerShape(20.dp))
            ) {
                TextField(
                    value = sortQuery,
                    onValueChange = {},
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        focusedContainerColor = Color(0xFFE2E2E2),
                        unfocusedContainerColor = Color(0xFFE2E2E2),
                        cursorColor = Color(0xFF0073FF),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .menuAnchor()
                        .background(Color.Transparent),
                    placeholder = {
                        Text(
                            text = "Sort by",
                            style = LocalTextStyle.current.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.White)
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(text = "Newest to Oldest")
                        },
                        onClick = {
                            sortQuery = "Newest to Oldest"
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = "Oldest to Newest")
                        },
                        onClick = {
                            sortQuery = "Oldest to Newest"
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = "A-Z")
                        },
                        onClick = {
                            sortQuery = "A-Z"
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = "Z-A")
                        },
                        onClick = {
                            sortQuery = "Z-A"
                            expanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(tasks) { currentTask ->
                    Card (
                        onClick = {
                            navController.navigate(Screen.NewUpdateTaskScreen.route + "/${tasks.indexOf(currentTask)}")
                        },
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(70.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(0xFFE2E2E2))
                                .padding(5.dp)
                        ) {
                            var removing by remember {
                                mutableStateOf(false)
                            }
                            RadioButton(selected = removing,
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color(0xFF0073FF),
                                    unselectedColor = Color(0xFF0073FF)
                                ),
                                onClick = {
                                    removing = true
                                    tasks.get(tasks.indexOf(currentTask)).completed = true
                                    tasks.remove(currentTask)
                                    tasksManager.tasks.remove(currentTask)
                                    tasksManager.saveTasks()
                                    Toast.makeText(
                                        context,
                                        "Task Completed! Good Job :D",
                                        Toast.LENGTH_SHORT
                                        ).show()
                                    removing = false
                                }
                            )
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    text = currentTask.title,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = Color.Black
                                )
                                if (currentTask.description.isNotBlank())
                                    Text(
                                        text = currentTask.description,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Gray,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
        TextButton(
            onClick = {
                navController.navigate(Screen.NewUpdateTaskScreen.route + "/-1")
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(0.dp, 0.dp, 8.dp, 22.dp)
                .clip(RoundedCornerShape(20.dp))
                .size(65.dp)
                .background(Color(0xFF0073FF))
        ) {
            Icon(
                imageVector = Icons.Default.Create,
                contentDescription = "Add Note",
                modifier = Modifier
                    .size(30.dp),
                tint = Color.White
            )
        }
    }

}

@Composable
fun NewUpdateTaskScreen(tasksManager: TasksManager, navController: NavHostController, taskId: Int) {
    val task = if(taskId == -1) Task("", "") else tasksManager.tasks[taskId]


    var title by remember {
        mutableStateOf(task.title)
    }
    var description by remember {
        mutableStateOf(task.description)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "New Task",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 25.sp,
                modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
            )
            TextButton(
                onClick = {
                    if (title.isNotBlank()) {
                        if(taskId == -1)
                            tasksManager.tasks.add(0, Task(title, description))
                        else
                            tasksManager.tasks[taskId] = Task(title, description)

                        tasksManager.saveTasks()
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF0073FF))
            ) {
                Text(
                    text = if(taskId == -1) "Save" else "Update",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = title,
            onValueChange = {
                title = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp)),
            textStyle = MaterialTheme
                .typography
                .bodyMedium
                .copy(
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFE2E2E2),
                unfocusedContainerColor = Color(0xFFE2E2E2),
                focusedIndicatorColor = Color(0xFF0073FF),
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color(0xFF0073FF)
            ),
            placeholder = {
                Text(
                    text = "Title",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF949393)
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = description,
            onValueChange = {
                description = it
            },
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp)),
            textStyle = MaterialTheme
                .typography
                .bodyMedium
                .copy(
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFE2E2E2),
                unfocusedContainerColor = Color(0xFFE2E2E2),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color(0xFF0073FF)
            ),
            placeholder = {
                Text(
                    text = "Description",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF949393)
                )
            }
        )
    }
}

@Composable
fun HomeScreen() {
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    val items = listOf(
        BottomBarScreen.Starred,
        BottomBarScreen.Tasks
    )

    Scaffold (
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = (index == selectedIndex),
                        onClick = {
                            selectedIndex = index
                        },
                        label = {
                            Text(text = item.title)
                        },
                        icon = {
                            Icon(
                                imageVector = (
                                    if (index == selectedIndex)
                                        item.selectedIcon
                                    else
                                        item.unselectedIcon
                                    ),
                                contentDescription = item.title )
                        }
                    )
                }
            }
        }
    ) {
        it.calculateBottomPadding()
    }
}

@Preview( showBackground = true )
@Composable
private fun Preview() {
    val tasksManager = TasksManager(LocalContext.current)
    val navController = rememberNavController()
    MainScreen(tasksManager , navController)
}


