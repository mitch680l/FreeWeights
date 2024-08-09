package com.example.freeweights.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Insert
    suspend fun insertRegime(regime: Regime): Long

    @Insert
    suspend fun insertSet(set: Set)

    @Update
    suspend fun updateRegime(regime: Regime)

    @Update
    suspend fun updateSet(set: Set)

    @Delete
    suspend fun deleteRegime(regime: Regime)

    @Delete
    suspend fun deleteExercise(set: Set)

    @Query("SELECT * FROM Regime")
    fun getAllRegimes(): Flow<List<Regime>>

    @Query("SELECT * FROM Regime WHERE regimeId = :regimeId")
    fun getRegimeById(regimeId: Int): Flow<Regime>

    @Transaction
    @Query("SELECT * FROM Regime WHERE regimeId = :regimeId")
    fun getRegimeWithSets(regimeId: Int): Flow<RegimeWithSets>

}

data class RegimeWithSets(
    @Embedded val regime: Regime,
    @Relation(
        parentColumn = "regimeId",
        entityColumn = "regimeId"
    )
    val sets: List<Set>
)