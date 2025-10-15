package com.example.habitstracker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitCheckDao {
    @Query("SELECT * FROM habit_checks WHERE habitId = :habitId AND date = :date")
    suspend fun getByIdAndDate(habitId: Long, date: Long): HabitCheckEntity?

    @Query("SELECT * FROM habit_checks WHERE date = :date")
    fun getForDate(date: Long): Flow<List<HabitCheckEntity>>

    @Insert
    suspend fun insert(habitCheck: HabitCheckEntity)

    @Query("DELETE FROM habit_checks WHERE habitId = :habitId AND date = :date")
    suspend fun delete(habitId: Long, date: Long)
}
