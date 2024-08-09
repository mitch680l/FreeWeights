package com.example.freeweights.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.freeweights.AppViewModelProvider
import com.example.freeweights.data.Exercise
import com.example.freeweights.NavBar


@Composable
fun ExerciseScreen(navController: NavHostController,
                   viewModel: ExerciseViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val exercisesState = remember { mutableStateOf<List<Exercise>>(emptyList()) }
    //val exercises by viewModel.exercises.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    val onDelete: (Exercise) -> Unit = { exercise ->
        viewModel.deleteExercise(exercise)
    }
    val onEdit: (Exercise, Int) -> Unit = { exercise, max ->
        exercise.max = max
        viewModel.updateExercise(exercise) }

    LaunchedEffect(Unit) { // Collect exercises in a LaunchedEffect
        viewModel.exercises.collect { exercises ->
            exercisesState.value = exercises
        }
    }

    Scaffold(bottomBar = { NavBar(navController) }, containerColor = MaterialTheme.colorScheme.primaryContainer) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Exercises",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp),
            )

            LazyColumn() {
                items(count = exercisesState.value.size, key = { index -> exercisesState.value[index].id }) { exerciseIndex ->
                    val exerciseState = remember(exerciseIndex) { mutableStateOf(exercisesState.value[exerciseIndex]) }
                    ExerciseItem(exerciseState, onDelete, onEdit)
                }
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { showDialog = true }, modifier = Modifier.align(Alignment.Center)) {
                    Text("Add Exercise")
                }
            }

            if (showDialog) {
                var newExercise by remember { mutableStateOf(NewExercise()) }
                AddExerciseDialog(
                    newExercise = newExercise,
                    onExerciseChange = { updatedExercise ->
                        newExercise = updatedExercise
                    },
                    onSave = { updatedExercise ->
                        val newEx = updatedExercise.toExercise()
                        if (newExercise.verify()) {

                            viewModel.addExercise(newEx)
                        }
                        else {
                            viewModel.addExercise(newEx)
                        }

                        showDialog = false
                    },
                    onCancel = { showDialog = false }
                )
            }
        }
    }
}

@Composable
fun ExerciseItem(exerciseState: MutableState<Exercise>,
                 onDeleteClick: (Exercise) -> Unit,
                 onUpdateClick: (Exercise, Int) -> Unit,

) {
    val exercise = exerciseState.value
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.padding(8.dp).weight(3f)) {
                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 20.sp
                )
                Text(text = exercise.muscleGroup)
                Text(text = exercise.type)
            }
            Column(modifier = Modifier.padding(8.dp).weight(2f), horizontalAlignment = Alignment.CenterHorizontally) {
                var text = exercise.max.toString()

                if (exercise.type == "Bodyweight") {
                    text += " reps"
                }
                else {
                    text += " lbs"
                }
                Text(text = text, fontSize = 20.sp)


                Row(modifier = Modifier) {
                    IconButton(onClick = { exerciseState.value = exercise.copy(max = exercise.max - 1) // Update exercise within state
                        onUpdateClick(exerciseState.value, exercise.max - 1)  }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Minus 1"
                        )
                    }
                    IconButton(onClick = { exerciseState.value = exercise.copy(max = exercise.max + 1) // Update exercise within state
                        onUpdateClick(exerciseState.value, exercise.max + 1)  }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowForward,
                            contentDescription = "Plus 1"
                        )
                    }
                }

            }
            Column(modifier = Modifier.padding(8.dp).weight(1f)) {
                IconButton(onClick = { onDeleteClick(exercise) }) { // Delete button
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
            }



        }
    }

}



@Composable
fun AddExerciseDialog(
    newExercise: NewExercise,
    onExerciseChange: (NewExercise) -> Unit,
    onSave: (NewExercise) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf(newExercise.name) }
    var muscleGroup by remember { mutableStateOf(newExercise.muscleGroup) }
    var type by remember { mutableStateOf(newExercise.type) }
    var description by remember { mutableStateOf(newExercise.description) }
    var max by remember { mutableStateOf(newExercise.max) }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("Add New Exercise") },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Exercise Name") }
                )
                TextField(
                    value = muscleGroup,
                    onValueChange = { muscleGroup = it },
                    label = { Text("Muscle Group") }
                )
                TextField(
                    value = type,
                    onValueChange = { type = it },
                    label = { Text("Type") }
                )


                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") }
                )
                TextField(
                    value = max,
                    onValueChange = { max = it },
                    label = { Text("Max") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val updatedExercise = NewExercise(
                    name = name,
                    muscleGroup = muscleGroup,
                    type = type,
                    description = description,
                    max = max // Handle potential parsing errors
                )

                onSave(updatedExercise)
                onExerciseChange(updatedExercise)

            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text("Cancel")
            }
        }
    )
}



data class NewExercise(
    val name: String = "",
    val muscleGroup: String = "",
    val type: String= "",
    val description: String = "",
    val max: String = ""
) {
    fun verify(): Boolean {
        return name.isNotBlank() && muscleGroup.isNotBlank() &&
                type.isNotBlank() && description.isNotBlank() && max.isNotBlank()
    }
}


fun NewExercise.toExercise(): Exercise {
    return Exercise(
        name = this.name,
        muscleGroup = this.muscleGroup,
        type = this.type,
        description = this.description,
        max = this.max.toInt()
    )
}

