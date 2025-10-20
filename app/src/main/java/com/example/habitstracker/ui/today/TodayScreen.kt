package com.example.habitstracker.ui.today

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.habitstracker.domain.model.Habit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayScreen(
    state: TodayScreenState,
    onToggle: (habit: Habit, checked: Boolean) -> Unit,
    onAddHabit: () -> Unit,
    onOpenHabit: (id: Long) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddHabit) {
                Icon(Icons.Default.Add, contentDescription = "Add Habit")
            }
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(state.items) { item ->
                Row(
                    modifier = Modifier.clickable { onOpenHabit(item.habit.id) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    item.habit.emoji?.let { Text(text = it, modifier = Modifier.padding(16.dp)) }
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
}
