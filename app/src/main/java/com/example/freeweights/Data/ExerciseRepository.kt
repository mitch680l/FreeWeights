package com.example.freeweights.Data

import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    fun getAllExercises(): Flow<List<Exercise>>
    fun getExerciseById(id: Int): Flow<Exercise>
    fun getExerciseByName(name: String): Flow<Exercise>
    suspend fun addExercise(exercise: Exercise)
    suspend fun deleteExercise(exercise: Exercise)
}