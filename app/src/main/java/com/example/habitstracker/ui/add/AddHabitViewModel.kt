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

    fun addHabit(title: String, emoji: String?) {
        viewModelScope.launch {
            habitRepo.insertHabit(
                Habit(
                    id = 0, // auto-generates
                    title = title,
                    emoji = emoji ?: "", // Use provided emoji or empty string
                    daysOfWeek = setOf(1, 2, 3, 4, 5, 6, 7), // Every day for MVP
                    reminderHour = 9, // Default reminder
                    reminderMinute = 0
                )
            )
        }
    }
}
