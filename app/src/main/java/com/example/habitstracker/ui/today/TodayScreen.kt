package com.example.habitstracker.ui.today

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TodayScreen(
    state: TodayScreenState,
    onToggle: (habit: com.example.habitstracker.domain.model.Habit, checked: Boolean) -> Unit
) {
    LazyColumn {
        items(state.items) { item ->
            Row(
                modifier = Modifier.clickable { onToggle(item.habit, !item.checked) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = item.habit.emoji, modifier = Modifier.padding(16.dp))
                Text(text = item.habit.title, modifier = Modifier.weight(1f))
                Checkbox(
                    checked = item.checked,
                    onCheckedChange = { checked -> onToggle(item.habit, checked) },
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
