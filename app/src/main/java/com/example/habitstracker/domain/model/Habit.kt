package com.example.habitstracker.domain.model

data class Habit(
    val id: Long,
    val title: String,
    val emoji: String?,
    val daysOfWeek: Set<Int>,
    val reminderHour: Int?,
    val reminderMinute: Int?
)
