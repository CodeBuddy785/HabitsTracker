package com.example.habitstracker.ui.today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.core.Graph
import com.example.habitstracker.data.local.HabitCheckDao
import com.example.habitstracker.data.local.HabitCheckEntity
import com.example.habitstracker.data.repo.HabitRepository
import com.example.habitstracker.domain.model.Habit
import com.example.habitstracker.domain.util.DateUtils
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

data class TodayScreenState(val items: List<TodayItem> = emptyList())

data class TodayItem(
    val habit: Habit,
    val checked: Boolean
)

class TodayViewModel(
    private val habitRepo: HabitRepository = Graph.habitRepository,
    private val checkRepo: HabitCheckDao = Graph.habitCheckDao
) : ViewModel() {

    init {
        viewModelScope.launch {
            if (habitRepo.getHabits().first().isEmpty()) {
                habitRepo.insertHabit(
                    Habit(
                        id = 0,
                        title = "Drink Water",
                        emoji = "💧",
                        daysOfWeek = setOf(1, 2, 3, 4, 5, 6, 7),
                        reminderHour = 9,
                        reminderMinute = 0
                    )
                )
            }
        }
    }

    val state: StateFlow<TodayScreenState> = combine(
        habitRepo.getHabits(),
        checkRepo.getForDate(today())
    ) { habits, checks ->
        val items = habits
            .filter { it.daysOfWeek.contains(DateUtils.dayOfWeekToday()) }
            .map { habit ->
                TodayItem(
                    habit = habit,
                    checked = checks.any { check -> check.habitId == habit.id }
                )
            }
        TodayScreenState(items)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = TodayScreenState()
    )

    fun onToggle(habit: Habit, checked: Boolean) {
        viewModelScope.launch {
            if (checked) {
                checkRepo.insert(HabitCheckEntity(habit.id, today()))
            } else {
                checkRepo.delete(habit.id, today())
            }
        }
    }
}

private fun today(): Long {
    return Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis
}
