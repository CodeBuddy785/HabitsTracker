// REMOVED: package com.example.habitstracker.domain.repo
// REMOVED: import com.example.habitstracker.domain.model.Habit

package com.example.habitstracker.data.repo


import com.example.habitstracker.domain.model.Habit
import kotlinx.coroutines.flow.Flow


/**
 * An interface for the habit data source.
 */
interface HabitRepository {
    fun getHabits(): Flow<List<Habit>>
    suspend fun insertHabit(habit: Habit)
    suspend fun updateHabit(habit: Habit)
    suspend fun deleteHabit(habit: Habit)
}




