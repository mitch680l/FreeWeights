package com.example.freeweights

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.freeweights.data.myApplication
import com.example.freeweights.screens.ExerciseViewModel
import com.example.freeweights.screens.ProfileViewModel
import com.example.freeweights.screens.WorkoutViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ExerciseViewModel(myApplication().appContainer.exerciseRepository)
        }
        initializer {
            ProfileViewModel(myApplication().appContainer.profileRepository)
        }
        initializer {
            WorkoutViewModel(myApplication().appContainer.workoutRepository, myApplication().appContainer.exerciseRepository)
        }
    }
}

