package com.example.habitstracker.domain.util

import java.util.Calendar

object DateUtils {
    fun dayOfWeekToday(): Int {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    }
}
