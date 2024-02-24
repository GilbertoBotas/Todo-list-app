package com.gilib.taskit.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilib.taskit.model.Graph
import com.gilib.taskit.model.Task
import com.gilib.taskit.model.TasksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TaskViewModel(
    private val tasksRepository: TasksRepository = Graph.tasksRepository
): ViewModel() {

    data class ListState(
        val taskList: Flow<List<Task>>,
        val starredList: Flow<List<Task>>,
        val completedList: Flow<List<Task>>
    )

    var selectedTaskTabState by mutableIntStateOf(1)
    var taskTitleState by mutableStateOf("")
    var taskDescriptionState by mutableStateOf("")
    var taskStarredState by mutableStateOf(false)

    fun onSelectedTaskTabChange(tab: Int) {
        selectedTaskTabState = tab
    }

    fun onTitleChange(title: String) {
        taskTitleState = title
    }

    fun onDescriptionChange(description: String) {
        taskDescriptionState = description
    }

    lateinit var listState: MutableState<ListState>

    init {
        viewModelScope.launch {
            listState = mutableStateOf(
                    ListState(
                    taskList = tasksRepository.getNewestTasks(),
                    starredList = tasksRepository.getNewestStarredTasks(),
                    completedList = tasksRepository.getCompletedTasks()
                )
            )
        }
    }

    private fun updateListState(taskList: Flow<List<Task>>, starredList: Flow<List<Task>>) {
        listState.value = listState.value.copy(taskList = taskList, starredList = starredList)
    }

    fun newest() {
        viewModelScope.launch {
            updateListState(
                taskList = tasksRepository.getNewestTasks(),
                starredList = tasksRepository.getNewestStarredTasks()
            )
        }
    }

    fun oldest() {
        viewModelScope.launch {
            updateListState(
                taskList = tasksRepository.getOldestTasks(),
                starredList = tasksRepository.getOldestStarredTasks()
            )
        }
    }

    fun titleAZ() {
        viewModelScope.launch {
            updateListState(
                taskList = tasksRepository.getAZTasks(),
                starredList = tasksRepository.getAZStarredTasks()
            )
        }
    }

    fun titleZA() {
        viewModelScope.launch {
            updateListState(
                taskList = tasksRepository.getZATasks(),
                starredList = tasksRepository.getZAStarredTasks()
            )
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.addTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.updateTask(task)
        }
    }

    fun getTask(id: Long): Flow<Task> {
        return tasksRepository.getTask(id)
    }

}