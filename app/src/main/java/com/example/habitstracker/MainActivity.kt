package com.example.habitstracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.ui.add.AddHabitScreen
import com.example.habitstracker.ui.theme.HabitsTrackerTheme
import com.example.habitstracker.ui.today.TodayScreen
import com.example.habitstracker.ui.today.TodayViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HabitsTrackerTheme {
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val nav = rememberNavController()
                    NavHost(
                        navController = nav,
                        startDestination = "today"
                    ) {
                        composable("today") {
                            val vm: TodayViewModel = viewModel()
                            TodayScreen(
                                state = vm.state.collectAsState().value,
                                onToggle = vm::onToggle,
                                onAddHabit = { nav.navigate("add") }
                            )
                        }
                        composable("add") {
                            AddHabitScreen(
                                onDone = { nav.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
