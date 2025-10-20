package com.example.habitstracker

import android.app.Application
import com.example.habitstracker.core.Graph
import com.example.habitstracker.core.Notify
import com.example.habitstracker.core.ReminderScheduler

class HabitsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
        Notify.ensureChannel(this)
        ReminderScheduler.scheduleAll(this)
    }
}
