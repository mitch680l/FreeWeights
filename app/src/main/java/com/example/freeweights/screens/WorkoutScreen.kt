package com.example.freeweights.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.freeweights.AppViewModelProvider
import com.example.freeweights.NavBar
import com.example.freeweights.data.Regime

@Composable
fun WorkoutScreen(navController: NavHostController, viewModel: WorkoutViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val workouts by viewModel.workouts.collectAsState()
    val showDialog = remember { mutableStateOf(false) }
    Scaffold(bottomBar = { NavBar(navController) }, containerColor = MaterialTheme.colorScheme.primaryContainer, contentColor = MaterialTheme.colorScheme.onPrimaryContainer)  { innerPadding ->


        Column(modifier = Modifier.padding(innerPadding), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {

            Box(modifier = Modifier.padding(16.dp)) {
                Text("Workouts", fontSize = MaterialTheme.typography.headlineLarge.fontSize)
            }
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(workouts) { workout ->
                    WorkoutCard(workout, navController)
                }
            }
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(
                    onClick = { showDialog.value = true },
                ) {
                    Text("Add Workout")
                }
            }





            if (showDialog.value) {
                AddRegimeDialog(
                    onDismissRequest = { showDialog.value = false },
                    onSave = { newRegime ->
                        viewModel.addWorkout(newRegime) // Assuming you have this function in your ViewModel
                        showDialog.value = false
                    }
                )
            }
        }
    }
}


@Composable
fun WorkoutCard(workout: Regime, navController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier
                .padding(16.dp)
                .weight(1f)) {
                Text(text = workout.name, style = MaterialTheme.typography.headlineSmall)
            }
            Text(text = workout.description, modifier = Modifier
                .padding(4.dp)
                .weight(3f))
            IconButton(onClick = {navController.navigate("regime${workout.regimeId}" )}, modifier = Modifier.weight(1f)) {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play")

            }
        }

    }
}


@Composable
fun AddRegimeDialog(onDismissRequest: () -> Unit, onSave: (Regime) -> Unit) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var numberOfCompletion by remember { mutableIntStateOf(0) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Add New Workout") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") }
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") }
                )
                // You might need a different input for numberOfCompletion (e.g., NumberPicker)
                OutlinedTextField(
                    value = numberOfCompletion.toString(),
                    onValueChange = { numberOfCompletion = it.toIntOrNull() ?: 0 },label = { Text("Number of Completions") }
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val newRegime = Regime(name = name, description = description, numberOfCompletion = numberOfCompletion)
                onSave(newRegime)
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text("Cancel")
            }
        }
    )
}
