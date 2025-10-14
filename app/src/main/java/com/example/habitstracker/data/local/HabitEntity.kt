package com.example.habitstracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val emoji: String,
    val daysOfWeekMask: Int,
    val reminderHour: Int,
    val reminderMinute: Int
)
