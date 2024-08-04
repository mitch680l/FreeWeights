package com.example.freeweights

import android.app.Application
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.freeweights.Data.myApplication
import com.example.freeweights.Screens.ExerciseViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ExerciseViewModel(myApplication().appContainer.exerciseRepository)
        }
    }
}

