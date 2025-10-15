package com.example.habitstracker.core

import android.content.Context
import androidx.room.Room
import com.example.habitstracker.data.local.AppDatabase
import com.example.habitstracker.data.local.HabitCheckDao
import com.example.habitstracker.data.repo.HabitRepository
import com.example.habitstracker.data.repo.RoomHabitRepository

object Graph {
    lateinit var database: AppDatabase
        private set

    val habitRepository: HabitRepository by lazy {
        RoomHabitRepository(database.habitDao())
    }

    val habitCheckDao: HabitCheckDao
        get() = database.habitCheckDao()

    fun provide(context: Context) {
        database = Room.databaseBuilder(context, AppDatabase::class.java, "habits.db").build()
    }
}
