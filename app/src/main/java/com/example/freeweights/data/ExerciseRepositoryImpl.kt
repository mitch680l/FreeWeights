package com.example.freeweights.data

import kotlinx.coroutines.flow.Flow

class ExerciseRepositoryImpl(private val exerciseDao: ExerciseDao) : ExerciseRepository {
    override fun getAllExercises(): Flow<List<Exercise>> {
        return exerciseDao.getAll()
    }
    override fun getExerciseById(id: Int): Flow<Exercise> {
        return exerciseDao.getById(id)
    }

    override suspend fun deleteExercise(exercise: Exercise) {
        return exerciseDao.delete(exercise)
    }

    override suspend fun addExercise(exercise: Exercise) {
        return exerciseDao.insert(exercise)
    }

    override fun getExerciseByName(name: String): Flow<Exercise> {
        return exerciseDao.getByName(name)

    }

    override suspend fun updateExercise(exercise: Exercise) {
        return exerciseDao.update(exercise)
    }

}