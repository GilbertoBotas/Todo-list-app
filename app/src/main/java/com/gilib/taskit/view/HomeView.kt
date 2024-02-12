package com.gilib.taskit.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gilib.taskit.R
import com.gilib.taskit.model.Task
import com.gilib.taskit.ui.theme.TaskItTheme
import com.gilib.taskit.viewModel.TaskViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeView(
    navController: NavController,
    viewModel: TaskViewModel
) {
    //Variables for Tabbed Row
    val items = listOf(
        TabRowItem.Starred,
        TabRowItem.Tasks
    )
    val pagerState = rememberPagerState {
        items.size
    }

    //UX for the Tabbed Row
    LaunchedEffect(viewModel.selectedTaskTabState) {
        pagerState.animateScrollToPage(viewModel.selectedTaskTabState)
    }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if(!pagerState.isScrollInProgress)
            viewModel.onSelectedTaskTabChange(pagerState.currentPage)
    }

    Scaffold (
        topBar = { AppBarView(title = "TaskIt") },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all = 20.dp),
                onClick = {
                    val starred = viewModel.selectedTaskTabState == 0
                    navController.navigate(Screen.NewUpdateTaskScreen.route + "/0L/$starred")
                },
                contentColor = Color.White,
                containerColor = colorResource(id = R.color.app_bar_color)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Button")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            TabRow(
                selectedTabIndex = viewModel.selectedTaskTabState,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[viewModel.selectedTaskTabState]),
                        color = colorResource(id = R.color.app_bar_color)
                    )
                },
                containerColor = Color.Transparent
            ) {
                items.forEachIndexed { index, item ->
                    Tab(
                        selected = (index == viewModel.selectedTaskTabState),
                        onClick = {
                            viewModel.selectedTaskTabState = index
                        },
                        text = {
                            Text(text = item.title)
                        },
                        icon = {
                            Icon(
                                imageVector = (
                                        if (index == viewModel.selectedTaskTabState)
                                            item.selectedIcon
                                        else
                                            item.unselectedIcon
                                        ),
                                contentDescription = item.title
                            )
                        },
                        selectedContentColor = colorResource(id = R.color.app_bar_color),
                        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) { index ->
                Column (
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top
                ){
                    if(index == 0) {
                        ListTasksView(
                            starred = true,
                            viewModel = viewModel,
                            navController = navController)
                    } else {
                        ListTasksView(
                            modifier = Modifier.weight(2f),
                            starred = false,
                            viewModel = viewModel,
                            navController = navController
                        )
                        ListCompletedTasks(
                            modifier = Modifier.weight(1f),
                            viewModel = viewModel
                        )
                    }
                }

            }

        }

    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    TaskItTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            TaskItem(task = Task(1,"test", "description"), {}, {})
        }
    }
}