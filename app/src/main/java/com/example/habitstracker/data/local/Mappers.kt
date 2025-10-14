package com.example.habitstracker.data.local

import com.example.habitstracker.domain.model.Habit
import com.example.habitstracker.domain.util.DaysMask

fun Habit.toEntity() = HabitEntity(
    id = id,
    title = title,
    emoji = emoji,
    daysOfWeekMask = DaysMask.fromSet(daysOfWeek),
    reminderHour = reminderHour,
    reminderMinute = reminderMinute
)

fun HabitEntity.toModel() = Habit(
    id = id,
    title = title,
    emoji = emoji,
    daysOfWeek = DaysMask.toSet(daysOfWeekMask),
    reminderHour = reminderHour,
    reminderMinute = reminderMinute
)
