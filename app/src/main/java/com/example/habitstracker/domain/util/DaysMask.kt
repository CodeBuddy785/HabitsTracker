package com.example.habitstracker.domain.util

import com.example.habitstracker.domain.model.Habit

object DaysMask {
    private const val SUNDAY = 1
    private const val MONDAY = 2
    private const val TUESDAY = 4
    private const val WEDNESDAY = 8
    private const val THURSDAY = 16
    private const val FRIDAY = 32
    private const val SATURDAY = 64

    fun fromSet(days: Set<Int>): Int {
        var mask = 0
        if (days.contains(1)) mask = mask or SUNDAY
        if (days.contains(2)) mask = mask or MONDAY
        if (days.contains(3)) mask = mask or TUESDAY
        if (days.contains(4)) mask = mask or WEDNESDAY
        if (days.contains(5)) mask = mask or THURSDAY
        if (days.contains(6)) mask = mask or FRIDAY
        if (days.contains(7)) mask = mask or SATURDAY
        return mask
    }

    fun toSet(mask: Int): Set<Int> {
        val days = mutableSetOf<Int>()
        if (mask and SUNDAY > 0) days.add(1)
        if (mask and MONDAY > 0) days.add(2)
        if (mask and TUESDAY > 0) days.add(3)
        if (mask and WEDNESDAY > 0) days.add(4)
        if (mask and THURSDAY > 0) days.add(5)
        if (mask and FRIDAY > 0) days.add(6)
        if (mask and SATURDAY > 0) days.add(7)
        return days
    }
}
