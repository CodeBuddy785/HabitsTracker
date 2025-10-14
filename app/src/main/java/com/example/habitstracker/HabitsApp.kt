package com.example.habitstracker

import android.app.Application
import com.example.habitstracker.core.Graph

class HabitsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}
