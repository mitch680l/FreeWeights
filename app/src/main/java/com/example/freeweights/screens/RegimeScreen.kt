package com.example.freeweights.screens

import android.app.AlertDialog
import android.provider.CalendarContract
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.freeweights.AppViewModelProvider
import com.example.freeweights.NavBar
import com.example.freeweights.R
import com.example.freeweights.data.Exercise
import com.example.freeweights.data.Regime
import com.example.freeweights.data.Set
import kotlinx.coroutines.flow.first
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize

@Composable
fun RegimeScreen(
    navController: NavHostController,
    regimeId: Int,
    viewModel: WorkoutViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val regime by viewModel.getRegimeWithId(regimeId).collectAsState(initial = null)
    val sets by viewModel.getRegimeWithSets(regimeId).collectAsState(initial = null)
    val setListState = remember { mutableStateOf<List<Set>>(emptyList()) }
    setListState.value = sets?.sets ?: emptyList()
    var showSetDialog by remember { mutableStateOf(false) }
    val exercises by viewModel.getExercises().collectAsState(initial = emptyList())

    val onUpdateClick: (Set, Int) -> Unit = { set, amount ->
        val updatedSet = set.copy(sets = set.sets + amount)
        val index = setListState.value.indexOf(set)
        if (index != -1) {
            val updatedList = setListState.value.toMutableList()
            if (amount < 0 && updatedSet.sets <= 0) {
                updatedList.removeAt(index)
                viewModel.deleteSet(set)
            } else {
                updatedList[index] = updatedSet
                viewModel.updateSet(updatedSet)
            }
            setListState.value = updatedList
        }
    }

    val onDeleteClick: (Set) -> Unit = { set ->
        val index = setListState.value.indexOf(set)
        if (index != -1) {val updatedList = setListState.value.toMutableList()
            updatedList.removeAt(index)
            setListState.value = updatedList
            viewModel.deleteSet(set)
        }
    }

    Scaffold(
        bottomBar = { NavBar(navController) },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                regime?.let {
                    Text(
                        it.name,
                        style = MaterialTheme.typography.headlineLarge
                    )
                    Box {
                        Button(onClick = { showSetDialog = true }) {
                            Text("Add Set")
                        }
                    }
                    IconButton(
                        onClick = { navController.navigate("workout") },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.onBackground
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            }
            LazyColumn {
                items(
                    items = setListState.value,
                    key = { set -> set.id } // Provide a unique key for each Set item
                ) { set ->
                    SetCard(set, viewModel, onUpdateClick, onDeleteClick)
                }

            }

        }

        if (showSetDialog) {
            AddSetDialog(
                regimeId = regimeId,
                onDismissRequest = { showSetDialog = false },
                onSave = { newSet ->
                    viewModel.addSet(newSet)
                    showSetDialog = false
                },
                exercises = exercises
            )
        }
    }
}

@Composable
fun SetCard(
    set: Set,
    viewModel: WorkoutViewModel,
    onUpdateClick: (Set, Int) -> Unit,
    onDeleteClick: (Set) -> Unit
) {
    val exercise = viewModel.getExerciseById(set.exerciseId).collectAsState(initial = null)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                exercise.value?.let {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Row {
                    IconButton(onClick = { onUpdateClick(set, 1) }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Plus"
                        )
                    }
                    IconButton(onClick = { onUpdateClick(set, -1) }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Minus"
                        )
                    }
                    IconButton(onClick = { onDeleteClick(set) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Display RepBoxes
            for (i in 1..set.sets) {
                exercise.value?.let {
                    RepBox(set, viewModel, it)
                }
            }
        }
    }
}

@Composable
fun RepBox(set: Set, viewModel: WorkoutViewModel, exercise: Exercise) {
    Card(modifier = Modifier
        .padding(2.dp)
        .fillMaxWidth(),
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)

    ) {

        Text(text = set.reps.toString() + " reps of " + exercise.max.toString() + "lbs", modifier = Modifier.padding(2.dp))

    }
}




@Composable
fun AddSetDialog(regimeId: Int,exercises : List<Exercise>, onDismissRequest: () -> Unit, onSave: (Set) -> Unit) {
    var reps: String by remember { mutableStateOf("") }
    var setsNum: String by remember { mutableStateOf("") }
    var loadType: String by remember { mutableStateOf("") }
    val exerciseId: String by remember { mutableStateOf("") }
    var selectedExercise by remember { mutableStateOf<Exercise?>(null) }
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Add New Set") },
        text = {
            Column {
                OutlinedTextField(
                    value = reps.toString(),
                    onValueChange = { reps = it },
                    label = { Text("Number of Reps") }
                )
                OutlinedTextField(
                    value = setsNum.toString(),
                    onValueChange = { setsNum = it},
                    label = { Text("Number of Sets") }
                )
                OutlinedTextField(
                    value = loadType,
                    onValueChange = { loadType = it },
                    label = { Text("Load Type") }
                )


                Box {
                    OutlinedTextField(
                        value = selectedExercise?.name ?: "", // Display selected exercise name or empty string
                        onValueChange = { }, // Prevent direct text input
                        label = { Text("Exercise") },
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { expanded = true }) {
                                Icon(Icons.Filled.ArrowDropDown, "Dropdown")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                textFieldSize = coordinates.size.toSize()
                            }
                    )
                    DropdownMenu(
                        modifier = Modifier.width(with(LocalDensity.current) { textFieldSize.width.toDp() }),
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {

                        exercises.forEach { exercise ->
                            DropdownMenuItem(text = { Text(exercise.name, fontSize = 15.sp) }, onClick = { selectedExercise = exercise })

                        }

                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                selectedExercise?.let {
                val newSet = Set(reps = reps.toInt(), sets = setsNum.toInt(), loadType = loadType, exerciseId = selectedExercise!!.id, regimeId = regimeId)
                onSave(newSet)}
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text("Cancel")}
        }
    )
}