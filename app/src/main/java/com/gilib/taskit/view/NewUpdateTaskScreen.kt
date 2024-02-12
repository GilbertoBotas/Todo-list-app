package com.gilib.taskit.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gilib.taskit.R
import com.gilib.taskit.model.Task
import com.gilib.taskit.ui.theme.TaskItTheme
import com.gilib.taskit.viewModel.TaskViewModel

@Composable
fun NewUpdateTaskScreen(
    id: Long,
    viewModel: TaskViewModel,
    navController: NavController,
    starred: Boolean
) {

    val context = LocalContext.current

    val snackMessage = remember {
        mutableStateOf("")
    }

    if(id != 0L) {
        val task = viewModel.getTask(id).collectAsState(initial = Task())
        viewModel.taskTitleState = task.value.title
        viewModel.taskDescriptionState = task.value.description
        viewModel.taskStarredState = task.value.starred
    } else {
        viewModel.taskTitleState = ""
        viewModel.taskDescriptionState = ""
        viewModel.taskStarredState = starred
    }

    Scaffold(
        topBar = { AppBarView(
            title =
            if (id == 0L)
                stringResource(id = R.string.add_task)
            else
                stringResource(id = R.string.update_task),
            onBackNavClicked = { navController.navigateUp() }
        )}
    ) { it ->
        Column(
            modifier = Modifier
                .padding(it)
                .wrapContentSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TaskTextField(
                label = "Title",
                value = viewModel.taskTitleState,
                onValueChange = {
                    viewModel.onTitleChange(it)
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            TaskTextField(
                label = "Description",
                value = viewModel.taskDescriptionState,
                onValueChange = {
                    viewModel.onDescriptionChange(it)
                },
                modifier = Modifier.weight(1f),
                singleLine = false
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (viewModel.taskTitleState.isNotBlank()) {
                        if (id == 0L) {
                            viewModel.addTask(
                                Task(
                                    title = viewModel.taskTitleState.trim(),
                                    description = viewModel.taskDescriptionState.trim(),
                                    starred = starred
                                )
                            )
                            snackMessage.value = "Task has been created"
                        }
                        else {
                            viewModel.updateTask(
                                Task(
                                    id = id,
                                    title = viewModel.taskTitleState.trim(),
                                    description = viewModel.taskDescriptionState.trim(),
                                    starred = starred
                                )
                            )
                            snackMessage.value = "Task has been updated"
                        }
                        navController.navigateUp()
                    } else {
                        snackMessage.value = "Please fill the mandatory fields"
                    }
                    Toast.makeText(context, snackMessage.value, Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.app_bar_color),
                    contentColor = Color.White

                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text =
                    if (id == 0L)
                        stringResource(id = R.string.add_task)
                    else
                        stringResource(id = R.string.update_task),
                    style = TextStyle(
                        fontSize = 18.sp,
                    ),
                    fontWeight = FontWeight.ExtraBold
                )

            }
        }
    }
}

@Composable
fun TaskTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true
) {
    OutlinedTextField(
        value = value,
        singleLine = singleLine,
        onValueChange = onValueChange,
        label = { Text(text = label, fontWeight = FontWeight.ExtraBold) },
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colorScheme.inverseSurface,
            unfocusedBorderColor = MaterialTheme.colorScheme.inverseSurface,
            focusedBorderColor = colorResource(id = R.color.app_bar_color),
            cursorColor = colorResource(id = R.color.app_bar_color),
            focusedLabelColor = colorResource(id = R.color.app_bar_color)
        )
    )
}

@Preview (showBackground = true)
@Composable
fun Prev() {
    val viewModel: TaskViewModel = viewModel()
    TaskItTheme(darkTheme = true) {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            NewUpdateTaskScreen(id = 0, viewModel = viewModel, navController = rememberNavController(), starred = false)
        }
    }

}