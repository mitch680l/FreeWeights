package com.example.freeweights.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.freeweights.NavBar

@Composable
fun ProfileScreen(navController: NavHostController) {
    Scaffold(bottomBar = { NavBar(navController) })  { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text("Profile Screen")
        }

    }
}




