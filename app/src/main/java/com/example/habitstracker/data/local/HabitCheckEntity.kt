package com.example.habitstracker.data.local

import androidx.room.Entity

@Entity(tableName = "habit_checks", primaryKeys = ["habitId", "date"])
data class HabitCheckEntity(
    val habitId: Long,
    val date: Long
)
