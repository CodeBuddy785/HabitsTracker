package com.example.habitstracker.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekdayChips(
    selected: Set<Int>,
    onChange: (Set<Int>) -> Unit
) {
    val days = listOf("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa")
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        for ((i, day) in days.withIndex()) {
            val idx = i + 1
            FilterChip(
                selected = selected.contains(idx),
                onClick = {
                    val newSelection = if (selected.contains(idx)) {
                        selected - idx
                    } else {
                        selected + idx
                    }
                    onChange(newSelection)
                },
                label = { Text(day) }
            )
        }
    }
}
