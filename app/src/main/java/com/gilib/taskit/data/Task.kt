package com.gilib.taskit.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "task_table" )
data class Task(
    @PrimaryKey( autoGenerate = true )
    val id: Int = 1,
    var title: String,
    var description: String,
    var completed: Boolean = false
)