package com.gilib.taskit.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gilib.taskit.R
import com.gilib.taskit.model.Task
import com.gilib.taskit.ui.theme.TaskItTheme
import com.gilib.taskit.viewModel.TaskViewModel

@Composable
fun AppBarView(
    title: String,
    onBackNavClicked: () -> Unit = {},
    viewModel: TaskViewModel = viewModel()
) {
    val navigationIcon: (@Composable () -> Unit)? =
        if(!title.contains("TaskIt")) {
            {
                IconButton(onClick = onBackNavClicked) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        } else {
            null
        }

    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .heightIn(max = 24.dp),
                    fontWeight = FontWeight.Bold
                )
                if(title.contains("TaskIt")) {
                    SortButton(viewModel = viewModel)
                }
            }

        },
        elevation = 3.dp,
        backgroundColor = colorResource(id = R.color.app_bar_color),
        navigationIcon = navigationIcon
    )
}

@Composable
fun TaskItem(
    task: Task,
    onEditClick: () -> Unit,
    onStarredClicked: () -> Unit,
    onDeleteClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable {
                onEditClick()
            },
        elevation = 10.dp,
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            RadioButton(
                selected = false,
                onClick = { onDeleteClicked() }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = task.title,
                        fontWeight = FontWeight.ExtraBold,
                        maxLines = 1
                    )
                    Text(
                        text = task.description,
                        maxLines = 1
                    )
                }

                TaskCheckBox(modifier = Modifier.padding(8.dp), checked = task.starred) {
                    onStarredClicked()
                }
            }
        }
    }
}

@Composable
fun TaskCheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: () -> Unit
) {
    Icon(
        imageVector = if(checked) Icons.Rounded.Star else Icons.TwoTone.Star,
        contentDescription = "Custom Checkbox",
        tint = if(checked) Color.Yellow else Color.Gray,
        modifier = modifier
            .size(30.dp)
            .clickable { onCheckedChange() }
    )
}

@Composable
fun SortButton(viewModel: TaskViewModel) {
    var expanded by remember {
        mutableStateOf(false)
    }

    val isNOSelected = remember {
        mutableStateOf(true)
    }

    val isONSelected = remember {
        mutableStateOf(false)
    }

    val isAZSelected = remember {
        mutableStateOf(false)
    }

    val isZASelected = remember {
        mutableStateOf(false)
    }

    Box {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Sort,
            contentDescription = "Filter",
            tint = Color.White,
            modifier = Modifier
                .padding(16.dp)
                .size(25.dp)
                .clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            TaskDropdownMenuItem(
                title = "Newest to Oldest",
                onClick = {
                    // DATABASE QUERY
                    viewModel.newest()
                    isAZSelected.value = false
                    isZASelected.value = false
                    isNOSelected.value = true
                    isONSelected.value = false
                    expanded = false
                },
                isSelected = isNOSelected
            )

            Divider(modifier = Modifier.fillMaxWidth())

            TaskDropdownMenuItem(
                title = "Oldest to Newest",
                onClick = {
                    // DATABASE QUERY
                    viewModel.oldest()
                    isAZSelected.value = false
                    isZASelected.value = false
                    isNOSelected.value = false
                    isONSelected.value = true
                    expanded = false
                },
                isSelected = isONSelected
            )

            Divider(modifier = Modifier.fillMaxWidth())

            TaskDropdownMenuItem(
                title = "Title (A-Z)",
                onClick = {
                    //DATABASE QUERY
                    viewModel.titleAZ()
                    isAZSelected.value = true
                    isZASelected.value = false
                    isNOSelected.value = false
                    isONSelected.value = false
                    expanded = false
                },
                isSelected = isAZSelected
            )

            Divider(modifier = Modifier.fillMaxWidth())

            TaskDropdownMenuItem(
                title = "Title (Z-A)",
                onClick = {
                    // DATABASE QUERY
                    viewModel.titleZA()
                    isAZSelected.value = false
                    isZASelected.value = true
                    isNOSelected.value = false
                    isONSelected.value = false
                    expanded = false
                },
                isSelected = isZASelected
            )
        }
    }
}

@Composable
fun TaskDropdownMenuItem(
    title: String,
    onClick: () -> Unit,
    isSelected: MutableState<Boolean>
) {
    DropdownMenuItem(
        text = {
            Text(text = title)
        },
        onClick = {
            onClick()
        },
        leadingIcon = {
            if(isSelected.value) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = colorResource(id = R.color.app_bar_color)
                )
            }
        }
    )
}

@Composable
fun NoTasksView(starred: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Error ,
            contentDescription = "No tasks",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(100.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "${if(starred) "No Starred" else "No"} tasks available!\n" +
                    "Create one by pressing the '+' button.",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewL() {
    TaskItTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            NoTasksView(starred = false)
        }
    }
}