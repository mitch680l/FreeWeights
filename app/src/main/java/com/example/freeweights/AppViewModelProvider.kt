package com.example.freeweights

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.freeweights.data.myApplication
import com.example.freeweights.screens.ExerciseViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ExerciseViewModel(myApplication().appContainer.exerciseRepository)
        }
    }
}

