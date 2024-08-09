package com.example.freeweights.screens

import android.widget.Button
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.freeweights.AppViewModelProvider
import com.example.freeweights.NavBar
import com.example.freeweights.R
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import com.example.freeweights.data.Profile

@Composable
fun ProfileScreen(navController: NavHostController, viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val profiles by viewModel.profiles.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var goalWeight by remember { mutableStateOf("") }


    Scaffold(bottomBar = { NavBar(navController) },containerColor = MaterialTheme.colorScheme.primaryContainer, contentColor = MaterialTheme.colorScheme.onPrimaryContainer)  { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding), horizontalAlignment = Alignment.CenterHorizontally) {
            if (profiles.isEmpty()) { // Show "Create Profile" button if no profile exists
                Box(modifier = Modifier.padding(16.dp)) {
                    Text(stringResource(R.string.welcome), fontSize = 25.sp)

                }
                Box(modifier = Modifier.padding(8.dp)) {
                    Text(stringResource(R.string.profile_welcome_info))
                }
                Button(onClick = { showDialog = true }) {
                    Text("Create Profile")
                }
            } else {
                ProfileInfo(profiles[0])
            }


            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Create Your Profile") },
                    text = {
                        Column {
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = { Text("Name") }
                            )
                            OutlinedTextField(
                                value = weight,
                                onValueChange = { weight = it },
                                label = { Text("Weight (lbs)") }
                            )
                            OutlinedTextField(
                                value = goalWeight,
                                onValueChange = { goalWeight = it },
                                label = { Text("Goal Weight (lbs)") }
                            )
                        }
                    },
                    confirmButton = {
                        Button(onClick = {
                            val validWeight = if (weight.isNotBlank()) weight.toInt() else 0
                            val validGoalWeight = if (goalWeight.isNotBlank()) goalWeight.toInt() else 0
                            viewModel.addProfile(Profile(name = name, weight = validWeight, workoutsCompleted = 0, goalWeight = validGoalWeight))
                            showDialog = false
                        }) {
                            Text("Save")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDialog = false }) {
                            Text("Cancel")
                        }}
                )
            }
        }

    }

}

@Composable
fun ProfileInfo(profile: Profile) {
    val name = profile.name
    val weight = profile.weight
    val goalWeight = profile.goalWeight

    Card(
        modifier =Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
            Text(
                text = "Welcome, $name!",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Current Weight:",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "$weight lbs",style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Goal Weight:",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "$goalWeight lbs",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            val weightDifference = goalWeight - weight
            val differenceText = if (weightDifference > 0) {
                "You are $weightDifference lbs away from your goal!"
            } else if (weightDifference < 0) {
                "You have surpassed your goal by ${-weightDifference} lbs!"
            } else {
                "You have reached your goal!"
            }
            Text(
                text = differenceText,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }}
}



