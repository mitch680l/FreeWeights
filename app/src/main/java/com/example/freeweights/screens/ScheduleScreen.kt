package com.example.freeweights.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.freeweights.NavBar

@Composable
fun ScheduleScreen(navController: NavHostController) {
    Scaffold(bottomBar = { NavBar(navController) },containerColor = MaterialTheme.colorScheme.primaryContainer, contentColor = MaterialTheme.colorScheme.onPrimaryContainer)  { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text("ScheduleScreen")
        }

    }
}