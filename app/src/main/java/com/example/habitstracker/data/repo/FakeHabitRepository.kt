package com.example.habitstracker.data.repo

import com.example.habitstracker.domain.model.Habit
import com.example.habitstracker.domain.repo.HabitRepository // <-- ADD THIS LINE
import java.util.Calendar


class FakeHabitRepository : HabitRepository {

    private val habits = mutableListOf<Habit>()
    private val checks = mutableListOf<Pair<Long, Long>>()
    private var nextId = 1L

    // ... rest of your file
}


// Helper function for your tests to set up data
    fun addHabit(habit: Habit) {
        habits.add(habit.copy(id = nextId++))
    }

    override suspend fun getHabits(): List<Habit> {
        return habits.toList()
    }

    override suspend fun insertHabit(habit: Habit) {
        // This simulates inserting by adding to our in-memory list.
        if (habits.none { it.id == habit.id }) {
            habits.add(habit.copy(id = nextId++))
        }
    }

    override suspend fun isCheckedToday(habitId: Long): Boolean {
        val today = today()
        return checks.any { it.first == habitId && it.second == today }
    }

    // --- FIX: Corrected 'toggleToday' function signature ---
    // The interface in your other files expects this function to return nothing (Unit), not a Boolean.
    // This was the primary cause of your remaining errors.
    override suspend fun toggleToday(habitId: Long) {
        val today = today()
        val check = Pair(habitId, today)

        if (checks.contains(check)) {
            checks.remove(check)
        } else {
            checks.add(check)
        }
    }

    // This is a private helper function to get the start of today's date
    private fun today(): Long {
        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }
}


