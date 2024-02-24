package com.gilib.taskit.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "Task" )
data class Task(
    @PrimaryKey( autoGenerate = true )
    @ColumnInfo( name = "id" )
    val id: Long = 0,
    @ColumnInfo( name = "title" )
    var title: String = "",
    @ColumnInfo( name = "description" )
    var description: String = "",
    @ColumnInfo( name = "starred" )
    var starred: Boolean = false,
    @ColumnInfo( name = "completed" )
    var completed: Boolean = false
)