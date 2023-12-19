package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val tasksManager = TasksManager(this)
            val navController = rememberNavController()
            Navigator(tasksManager, navController).Navigation()
        }
    }
}

@Composable
fun UpdateTaskScreen(taskId: Int) {
//    val task = tasksManager.tasks[taskId]
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
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
                    if (title.isNotBlank() && description.isNotBlank()) {
//                        tasksManager.tasks[taskId] = Task(title, description)
//                        navController.navigate(Screen.MainScreen.route)
                    }
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF0073FF))
            ) {
                Text(
                    text = "Update",
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
                    color = Color.DarkGray
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
                    color = Color.DarkGray
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

@Preview(showBackground = true)
@Composable
fun Preview() {
    UpdateTaskScreen(taskId = 0)
}