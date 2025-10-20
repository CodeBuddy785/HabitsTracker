package com.example.habitstracker.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.core.Graph
import com.example.habitstracker.data.repo.HabitRepository
import com.example.habitstracker.domain.model.Habit
import kotlinx.coroutines.launch

class AddHabitViewModel(
    private val habitRepo: HabitRepository = Graph.habitRepository
) : ViewModel() {

    fun addHabit(
        title: String,
        emoji: String?,
        days: Set<Int>,
        hour: Int?,
        minute: Int?
    ) {
        viewModelScope.launch {
            habitRepo.insertHabit(
                Habit(
                    id = 0, // auto-generates
                    title = title,
                    emoji = emoji ?: "",
                    daysOfWeek = days,
                    reminderHour = hour ?: 9,
                    reminderMinute = minute ?: 0
                )
            )
        }
    }
}
