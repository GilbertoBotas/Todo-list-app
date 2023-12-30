package com.gilib.taskit

data class Task(
    var title: String,
    var description: String,
    var completed: Boolean = false
)