package com.example.habitstracker.ui.today

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TodayScreen(
    vm: TodayViewModel = viewModel()
) {
    val items = vm.items.collectAsState().value

    LazyColumn {
        items(items) { item ->
            Row(
                modifier = Modifier.clickable { vm.toggle(item.habit.id) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = item.habit.emoji, modifier = Modifier.padding(16.dp))
                Text(text = item.habit.title, modifier = Modifier.weight(1f))
                Checkbox(
                    checked = item.checkedToday,
                    onCheckedChange = { vm.toggle(item.habit.id) },
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
