package com.example.habitstracker.ui.add

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habitstracker.ui.components.WeekdayChips

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHabitScreen(
    onDone: () -> Unit,
    vm: AddHabitViewModel = viewModel()
) {
    var title by remember { mutableStateOf("") }
    var emoji by remember { mutableStateOf("") }
    var days by remember { mutableStateOf(setOf(1, 2, 3, 4, 5, 6, 7)) }
    var hour by remember { mutableStateOf("") }
    var minute by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Habit") },
                navigationIcon = {
                    IconButton(onClick = onDone) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { inner ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = emoji,
                onValueChange = { emoji = it },
                label = { Text("Emoji (optional)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Text("Repeat on:")
            WeekdayChips(selected = days, onChange = { days = it })
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = hour,
                    onValueChange = { hour = it.filter { ch -> ch.isDigit() }.take(2) },
                    label = { Text("Hour (0-23)") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = minute,
                    onValueChange = { minute = it.filter { ch -> ch.isDigit() }.take(2) },
                    label = { Text("Min (0-59)") },
                    singleLine = true
                )
            }
            Button(
                onClick = {
                    val h = hour.toIntOrNull()
                    val m = minute.toIntOrNull()
                    vm.addHabit(title, emoji.ifBlank { null }, days, h, m)
                    onDone()
                },
                enabled = title.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) { Text("Save") }
        }
    }
}
