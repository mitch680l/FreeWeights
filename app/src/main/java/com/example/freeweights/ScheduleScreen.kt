package com.example.freeweights

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun ScheduleScreen(navController: NavHostController) {
    Scaffold(bottomBar = {NavBar(navController)})  {innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text("ScheduleScreen")
        }

    }
}