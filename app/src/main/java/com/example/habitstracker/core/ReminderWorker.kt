package com.example.habitstracker.core

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.example.habitstracker.R
import com.example.habitstracker.core.Graph.habitRepository
import com.example.habitstracker.domain.util.DateUtils
import kotlinx.coroutines.flow.first

class ReminderWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val habitId = inputData.getLong("habitId", 0L)

        // Get the latest habit details from the database
        val habit = habitRepository.getHabit(habitId).first()

        // If habit has been deleted, stop the worker
        if (habit == null) {
            return Result.success()
        }

        val dueToday = habit.daysOfWeek.contains(DateUtils.dayOfWeekToday())

        if (dueToday) {
            Notify.ensureChannel(applicationContext)
            val mgr = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notif = NotificationCompat.Builder(applicationContext, Notify.CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Reminder")
                .setContentText("Time for: ${habit.title}")
                .setAutoCancel(true)
                .build()
            mgr.notify(habitId.toInt(), notif)
        }
        
        // Reschedule for the next day
        ReminderScheduler.scheduleNext(applicationContext, habitId)

        return Result.success()
    }

    companion object {
        fun input(habitId: Long) = Data.Builder()
            .putLong("habitId", habitId)
            .build()
    }
}
