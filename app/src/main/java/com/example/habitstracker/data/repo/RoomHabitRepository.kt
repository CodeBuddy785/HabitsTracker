package com.example.habitstracker.data.repo

import com.example.habitstracker.data.local.AppDatabase // Import AppDatabase
import com.example.habitstracker.data.local.HabitCheckDao
import com.example.habitstracker.data.local.HabitDao
import com.example.habitstracker.data.local.HabitCheckEntity // Import HabitCheckEntity
import com.example.habitstracker.data.local.toEntity
import com.example.habitstracker.data.local.toModel
import com.example.habitstracker.domain.model.Habit
import java.util.Calendar

// The class takes the 'AppDatabase' instance and initializes its DAOs from it.
class RoomHabitRepository(
    private val database: AppDatabase
) : HabitRepository {
    private val habitDao: HabitDao = database.habitDao()
    private val habitCheckDao: HabitCheckDao = database.habitCheckDao()

    override suspend fun getHabits(): List<Habit> {
        return habitDao.getAll().map { it.toModel() }
    }

    override suspend fun insertHabit(habit: Habit) {
        habitDao.insert(habit.toEntity())
    }

    override suspend fun isCheckedToday(habitId: Long): Boolean {
        return habitCheckDao.getByIdAndDate(habitId, today()) != null
    }

    override suspend fun toggleToday(habitId: Long) {
        val today = today()
        if (isCheckedToday(habitId)) {
            habitCheckDao.delete(habitId, today)
        } else {
            habitCheckDao.insert(
                HabitCheckEntity(
                    habitId = habitId,
                    date = today
                )
            )
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
}
