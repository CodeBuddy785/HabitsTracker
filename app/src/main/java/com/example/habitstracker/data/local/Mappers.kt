package com.example.habitstracker.data.local

import com.example.habitstracker.domain.model.Habit
import com.example.habitstracker.domain.util.DaysMask

fun Habit.toEntity() = HabitEntity(
    id = id,
    title = title,
    emoji = emoji ?: "",
    daysOfWeekMask = DaysMask.fromSet(daysOfWeek),
    reminderHour = reminderHour ?: 9,
    reminderMinute = reminderMinute ?: 0
)

fun HabitEntity.toModel() = Habit(
    id = id,
    title = title,
    emoji = emoji,
    daysOfWeek = DaysMask.toSet(daysOfWeekMask),
    reminderHour = reminderHour,
    reminderMinute = reminderMinute
)
