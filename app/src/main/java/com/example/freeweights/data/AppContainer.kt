package com.example.freeweights.data

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.freeweights.MyApp

interface AppContainer {
    val exerciseRepository: ExerciseRepository
    val profileRepository: ProfileRepository
    val workoutRepository: WorkoutRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val exerciseRepository: ExerciseRepository by lazy {
        ExerciseRepositoryImpl(AppDatabase.getDatabase(context).exerciseDao())
    }
    override val profileRepository: ProfileRepository by lazy {
        ProfileRepository(AppDatabase.getDatabase(context).profileDao())
    }
    override val workoutRepository: WorkoutRepository by lazy {
        WorkoutRepository(AppDatabase.getDatabase(context).workoutDao())
    }
}


fun CreationExtras.myApplication(): MyApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApp)