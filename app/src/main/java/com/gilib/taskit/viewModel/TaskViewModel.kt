package com.gilib.taskit.viewModel

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

    lateinit var getAllTasks: Flow<List<Task>>
    lateinit var getStarredTasks: Flow<List<Task>>
    lateinit var getCompletedTasks: Flow<List<Task>>

    init {
        viewModelScope.launch {
            getAllTasks = tasksRepository.getAllTasks()
            getStarredTasks = tasksRepository.getStarredTasks()
            getCompletedTasks = tasksRepository.getCompletedTasks()
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