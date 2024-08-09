package com.example.freeweights.data

import kotlinx.coroutines.flow.Flow

class WorkoutRepository(private val workoutDao: WorkoutDao) {
    suspend fun insertRegime(regime: Regime) = workoutDao.insertRegime(regime)

    suspend fun insertSet(set: Set) = workoutDao.insertSet(set)

    suspend fun updateRegime(regime: Regime) =workoutDao.updateRegime(regime)

    suspend fun updateSet(set: Set) = workoutDao.updateSet(set)

    suspend fun deleteRegime(regime: Regime) = workoutDao.deleteRegime(regime)

    suspend fun deleteSet(set: Set) = workoutDao.deleteExercise(set)

    suspend fun getAllRegimes() = workoutDao.getAllRegimes()

    fun getRegimeById(regimeId: Int): Flow<Regime> = workoutDao.getRegimeById(regimeId)

    fun getRegimeWithSets(regimeId: Int): Flow<RegimeWithSets> =
        workoutDao.getRegimeWithSets(regimeId)

}