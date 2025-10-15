package com.example.habitstracker.domain.model

// --- FIX: Separated the import statements ---
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "habits") // Defines the table name
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val emoji: String,
    val daysOfWeek: Set<Int>,
    val reminderHour: Int,
    val reminderMinute: Int
)
