package com.example.habitstracker.data.repo

import com.example.habitstracker.data.local.HabitDao
import com.example.habitstracker.data.local.toEntity
import com.example.habitstracker.data.local.toModel
import com.example.habitstracker.domain.model.Habit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomHabitRepository(
    private val habitDao: HabitDao
) : HabitRepository {

    override fun getHabits(): Flow<List<Habit>> {
        return habitDao.getAll().map { entities ->
            entities.map { it.toModel() }
        }
    }

    override suspend fun insertHabit(habit: Habit) {
        habitDao.insert(habit.toEntity())
    }

    override suspend fun updateHabit(habit: Habit) {
        habitDao.update(habit.toEntity())
    }

    override suspend fun deleteHabit(habit: Habit) {
        habitDao.delete(habit.toEntity())
    }
}
