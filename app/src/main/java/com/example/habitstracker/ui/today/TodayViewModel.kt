package com.example.habitstracker.ui.today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.core.Graph
import com.example.habitstracker.data.repo.HabitRepository
import com.example.habitstracker.domain.model.Habit
import com.example.habitstracker.domain.util.DateUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class TodayItem(
    val habit: Habit,
    val dueToday: Boolean,
    val checkedToday: Boolean
)

class TodayViewModel(
    private val repo: HabitRepository = Graph.habitRepository
) : ViewModel() {

    private val _items = MutableStateFlow<List<TodayItem>>(emptyList())
    val items: StateFlow<List<TodayItem>> = _items

    fun load() {
        viewModelScope.launch {
            val all = repo.getHabits()
            val dow = DateUtils.dayOfWeekToday()
            val mapped = all.map { h ->
                val due = h.daysOfWeek.contains(dow)
                val checked = if (due) repo.isCheckedToday(h.id) else false
                TodayItem(h, due, checked)
            }.filter { it.dueToday }
            _items.value = mapped
        }
    }

    fun toggle(habitId: Long) {
        viewModelScope.launch {
            repo.toggleToday(habitId)
            load()
        }
    }

    /** One-time seeding so you can see data immediately */
    fun seedIfEmpty() {
        viewModelScope.launch {
            if (repo.getHabits().isEmpty()) {
                repo.insertHabit(
                    Habit(
                        id = 0,
                        title = "Drink Water",
                        emoji = "💧",
                        daysOfWeek = setOf(1,2,3,4,5,6,7),
                        reminderHour = 9,
                        reminderMinute = 0
                    )
                )
                load()
            }
        }
    }
}
