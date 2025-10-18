package com.example.habitstracker.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.core.Graph
import com.example.habitstracker.data.repo.HabitRepository
import com.example.habitstracker.domain.model.Habit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HabitDetailViewModel(
    private val habitRepo: HabitRepository = Graph.habitRepository
) : ViewModel() {

    private val _habit = MutableStateFlow<Habit?>(null)
    val habit: StateFlow<Habit?> = _habit

    fun load(id: Long) {
        viewModelScope.launch {
            habitRepo.getHabit(id).collect { 
                _habit.value = it
            }
        }
    }

    fun save(title: String, emoji: String?) {
        val h = habit.value ?: return
        viewModelScope.launch {
            habitRepo.updateHabit(h.copy(title = title, emoji = emoji ?: ""))
        }
    }

    fun delete() {
        val h = habit.value ?: return
        viewModelScope.launch {
            habitRepo.deleteHabit(h)
        }
    }
}
