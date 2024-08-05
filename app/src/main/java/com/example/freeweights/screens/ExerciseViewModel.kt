package com.example.freeweights.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freeweights.data.Exercise
import com.example.freeweights.data.ExerciseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ExerciseViewModel(private val exerciseRepository: ExerciseRepository) : ViewModel() {
    //val exercises = exerciseRepository.getAllExercises()
    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> = _exercises.asStateFlow()

    init {
        viewModelScope.launch {
            exerciseRepository.getAllExercises().collect { exercises ->
                _exercises.value = exercises
            }
        }
    }

    fun addExercise(exercise: Exercise) {
        viewModelScope.launch {
            exerciseRepository.addExercise(exercise)
            _exercises.value = exerciseRepository.getAllExercises().first()
        }
    }

    fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch {
            exerciseRepository.deleteExercise(exercise)
            _exercises.value = exerciseRepository.getAllExercises().first()
        }
    }

    fun getExerciseByName(name: String) {
        viewModelScope.launch {
            exerciseRepository.getExerciseByName(name)
        }
    }

    fun updateExercise(exercise: Exercise) {
        viewModelScope.launch {
            exerciseRepository.updateExercise(exercise)
            //_exercises.value = exerciseRepository.getAllExercises().first()
        }
    }
}