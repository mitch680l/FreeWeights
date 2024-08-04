package com.example.freeweights.Screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freeweights.Data.Exercise
import com.example.freeweights.Data.ExerciseRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExerciseViewModel(private val exerciseRepository: ExerciseRepository) : ViewModel() {
    val exercises = exerciseRepository.getAllExercises()

    fun addExercise(exercise: Exercise) {
        viewModelScope.launch {
            exerciseRepository.addExercise(exercise)
        }
    }

    fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch {
            exerciseRepository.deleteExercise(exercise)
        }
    }

    fun getExerciseByName(name: String) {
        viewModelScope.launch {
            exerciseRepository.getExerciseByName(name)
        }
    }


}