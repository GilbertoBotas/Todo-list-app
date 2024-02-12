package com.gilib.taskit

import android.app.Application
import com.gilib.taskit.model.Graph

class TaskApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}