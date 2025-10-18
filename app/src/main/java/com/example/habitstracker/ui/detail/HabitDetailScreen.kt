package com.example.habitstracker.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitDetailScreen(
    id: Long,
    onDone: () -> Unit,
    vm: HabitDetailViewModel = viewModel()
) {
    LaunchedEffect(id) { vm.load(id) }
    val habit = vm.habit.collectAsState().value

    var title by remember { mutableStateOf(habit?.title ?: "") }
    var emoji by remember { mutableStateOf(habit?.emoji ?: "") }

    // when habit loads, update fields
    LaunchedEffect(habit?.id) {
        title = habit?.title.orEmpty()
        emoji = habit?.emoji.orEmpty()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Habit Details") }) }
    ) { inner ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title, onValueChange = { title = it },
                label = { Text("Title") }, singleLine = true, modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = emoji, onValueChange = { emoji = it },
                label = { Text("Emoji") }, singleLine = true, modifier = Modifier.fillMaxWidth()
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = {
                        vm.save(title, emoji.ifBlank { null })
                        onDone()
                    },
                    enabled = title.isNotBlank()
                ) { Text("Save") }

                OutlinedButton(
                    onClick = {
                        vm.delete()
                        onDone()
                    }
                ) { Text("Delete") }
            }

            if (habit == null) {
                Text("Loading…", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
