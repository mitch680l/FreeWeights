package com.example.freeweights.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freeweights.data.Exercise
import com.example.freeweights.data.ExerciseRepository
import com.example.freeweights.data.Regime
import com.example.freeweights.data.RegimeWithSets
import com.example.freeweights.data.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.example.freeweights.data.Set
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map


class WorkoutViewModel(private val workoutRepository: WorkoutRepository, private val exerciseRepository: ExerciseRepository) : ViewModel() {
    //val exercises = exerciseRepository.getAllExercises()
    private val _workouts = MutableStateFlow<List<Regime>>(emptyList())
    val workouts: StateFlow<List<Regime>> = _workouts.asStateFlow()

    init {
        viewModelScope.launch {
            workoutRepository.getAllRegimes().collect { regime ->
                _workouts.value = regime
            }
        }
    }

    fun updateSet(set: Set) {
        viewModelScope.launch {
            workoutRepository.updateSet(set)
        }
    }
    fun getExercises(): Flow<List<Exercise>> {
        return exerciseRepository.getAllExercises()
    }
    fun deleteWorkout(workout: Regime) {
        viewModelScope.launch {
            workoutRepository.deleteRegime(workout)
            _workouts.value = workoutRepository.getAllRegimes().first()
        }
    }
    fun addWorkout(workout: Regime) {
        viewModelScope.launch {
            workoutRepository.insertRegime(workout)
            _workouts.value = workoutRepository.getAllRegimes().first()
        }
    }

    fun getRegimeWithId(regimeId: Int): Flow<Regime> {
         return workoutRepository.getRegimeById(regimeId)
    }

    fun deleteSet(set: Set) {
        viewModelScope.launch {
            workoutRepository.deleteSet(set)
        }
    }

    fun getRegimeWithSets(regimeId: Int): Flow<RegimeWithSets?> {return workoutRepository.getRegimeWithSets(regimeId).map { regimeWithSets ->
    regimeWithSets.let {
        val validSets = it.sets.filter { set ->
            val exerciseExists = getExerciseById(set.exerciseId).firstOrNull() != null
            if (!exerciseExists) {
                viewModelScope.launch {
                    workoutRepository.deleteSet(set)
                }
            }
            exerciseExists
        }
        it.copy(sets = validSets)
    }
    }
    }

    fun getExerciseById(id: Int): Flow<Exercise> {
        return exerciseRepository.getExerciseById(id)
    }

    fun addSet(set: Set) {
        viewModelScope.launch {
            workoutRepository.insertSet(set)
        }

    }



}