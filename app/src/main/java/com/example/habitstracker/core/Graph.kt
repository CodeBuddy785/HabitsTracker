package com.example.habitstracker.core

import android.content.Context
import androidx.room.Room
import com.example.habitstracker.data.local.AppDatabase
import com.example.habitstracker.data.repo.HabitRepository // Import the missing reference
import com.example.habitstracker.data.repo.RoomHabitRepository

object Graph {
    lateinit var database: AppDatabase

    val habitRepository: HabitRepository by lazy {
        RoomHabitRepository(database)
    }

    fun provide(context: Context) {
        database = Room.databaseBuilder(context, AppDatabase::class.java, "habits.db").build()
    }
}

