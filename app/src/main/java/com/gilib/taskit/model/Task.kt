package com.gilib.taskit.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "Task" )
data class Task(
    @PrimaryKey( autoGenerate = true )
    val id: Long = 0,
    var title: String = "",
    var description: String = "",
    var starred: Boolean = false,
    var completed: Boolean = false
)