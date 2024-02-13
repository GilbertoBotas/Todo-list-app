package com.gilib.taskit.view

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gilib.taskit.model.Task
import com.gilib.taskit.ui.theme.TaskItTheme
import com.gilib.taskit.viewModel.TaskViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListTasksView(
    modifier: Modifier = Modifier,
    starred: Boolean,
    viewModel: TaskViewModel,
    navController: NavController
) {

    val context = LocalContext.current

    val taskList = if(starred)
        viewModel.getStarredTasks.collectAsState(initial = listOf())
    else
        viewModel.getAllTasks.collectAsState(initial = listOf())

    LazyColumn (
        modifier = modifier
    ) {
        items(taskList.value, key = { task -> task.id }) { task ->
            TaskItem(
                task = task,
                onEditClick = {
                    val starredUpdate = task.starred
                    navController.navigate(Screen.NewUpdateTaskScreen.route + "/${task.id}/$starredUpdate")
                },
                onStarredClicked = {
                    viewModel.updateTask(task.copy(starred = !task.starred))
                },
                onDeleteClicked = {
                    viewModel.updateTask(task.copy(completed = true))
                    Toast.makeText(context, "Task Completed!", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}

@Composable
fun ListCompletedTasks(
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel
) {

    val showDialog = remember {
        mutableStateOf(false)
    }

    var selectedIncompleteTask by remember {
        mutableStateOf(Task())
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 2.dp,
            color = Color.LightGray
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Completed Tasks", fontWeight = FontWeight.Bold)

            Icon(
                imageVector =
                if(expanded)
                    Icons.Default.KeyboardArrowDown
                else
                    Icons.Default.KeyboardArrowUp,
                contentDescription =
                if(expanded)
                    "Completed tasks visible"
                else
                    "Completed tasks invisible"
            )
        }

        if(expanded) {
            val list = viewModel.getCompletedTasks.collectAsState(initial = listOf())

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(list.value) { task ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedIncompleteTask = task
                                showDialog.value = true
                            }
                            .padding(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Completed Task",
                            tint = Color.Green
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Text(
                            text = task.title,
                            textDecoration = TextDecoration.LineThrough,
                            maxLines = 1,
                        )
                    }
                }
            }
        }
    }
    if(showDialog.value) {
        MarkAsIncompleteDialog(
            showDialog = showDialog,
            onConfirm = {
                viewModel.updateTask(selectedIncompleteTask.copy(completed = false))
            },
            task = selectedIncompleteTask
        )
    }
}

@Composable
fun MarkAsIncompleteDialog(
    showDialog: MutableState<Boolean>,
    onConfirm: () -> Unit,
    task: Task
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { showDialog.value = false },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                    Toast.makeText(context, "Task marked incomplete!", Toast.LENGTH_SHORT).show()
                    showDialog.value = false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Green
                )
            ) {
                Text(text = "Yes")
            }
        },
        dismissButton = {
            Button(
                onClick = { showDialog.value = false },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Red
                )
            ) {
                Text(
                    text = "No",
                    fontWeight = FontWeight.Bold
                )
            }
        },
        title = {
            Text(
                text = "Mark Task as Incomplete?",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                Text(text = task.title, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = task.description)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ListingsPrev() {
    TaskItTheme (darkTheme = true) {
        Surface (color = MaterialTheme.colorScheme.background) {
//            MarkAsIncompleteDialog()
        }
    }
}