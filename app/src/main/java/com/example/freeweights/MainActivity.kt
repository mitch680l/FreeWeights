package com.example.freeweights

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.freeweights.ui.theme.FreeWeightsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FreeWeightsTheme {
                // A surface container using the 'background' color from the the
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "profile") {
        composable("exercise") { ExerciseScreen(navController) }
        composable("schedule") { ScheduleScreen(navController) }
        composable("workout") { WorkoutScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
    }
}