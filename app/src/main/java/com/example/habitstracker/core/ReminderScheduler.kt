package com.example.habitstracker.core

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.habitstracker.domain.model.Habit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.concurrent.TimeUnit

object ReminderScheduler {

    fun scheduleAll(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val habits = Graph.habitRepository.getHabits().first()
            habits.forEach { habit ->
                schedule(context, habit)
            }
        }
    }

    fun schedule(context: Context, habit: Habit) {
        val workManager = WorkManager.getInstance(context)
        val tag = "habit-${habit.id}"

        val now = Calendar.getInstance()
        val due = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, habit.reminderHour ?: 9)
            set(Calendar.MINUTE, habit.reminderMinute ?: 0)
            set(Calendar.SECOND, 0)
        }
        if (due.before(now)) {
            due.add(Calendar.HOUR_OF_DAY, 24)
        }

        val initialDelay = due.timeInMillis - now.timeInMillis

        val input = ReminderWorker.input(habit.id)

        val request = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .setInputData(input)
            .addTag(tag)
            .build()

        workManager.enqueueUniqueWork(tag, ExistingWorkPolicy.REPLACE, request)
    }

    fun scheduleNext(context: Context, habitId: Long) {
        val workManager = WorkManager.getInstance(context)
        val request = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(24, TimeUnit.HOURS)
            .setInputData(ReminderWorker.input(habitId))
            .build()

        workManager.enqueue(request)
    }

    fun cancel(context: Context, habit: Habit) {
        val tag = "habit-${habit.id}"
        WorkManager.getInstance(context).cancelAllWorkByTag(tag)
    }
}
