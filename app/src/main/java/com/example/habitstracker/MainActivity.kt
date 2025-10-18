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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.habitstracker.ui.add.AddHabitScreen
import com.example.habitstracker.ui.detail.HabitDetailScreen
import com.example.habitstracker.ui.theme.HabitsTrackerTheme
import com.example.habitstracker.ui.today.TodayScreen
import com.example.habitstracker.ui.today.TodayViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HabitsTrackerTheme {
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

                    // 🔹 Create navigation controller
                    val nav = rememberNavController()

                    // 🔹 Define navigation graph
                    NavHost(
                        navController = nav,
                        startDestination = "today"
                    ) {
                        // 🟢 Today Screen
                        composable("today") {
                            val vm: TodayViewModel = viewModel()
                            TodayScreen(
                                state = vm.state.collectAsState().value,
                                onToggle = vm::onToggle,
                                onAddHabit = { nav.navigate("add") },
                                onOpenHabit = { id -> nav.navigate("detail/$id") }
                            )
                        }

                        // 🟣 Add Habit Screen
                        composable("add") {
                            AddHabitScreen(
                                onDone = { nav.popBackStack() }
                            )
                        }

                        // 🔵 Detail Screen
                        composable(
                            route = "detail/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.LongType })
                        ) {
                            HabitDetailScreen(
                                id = it.arguments?.getLong("id") ?: 0L,
                                onDone = { nav.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
