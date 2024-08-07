package com.example.freeweights.data

@androidx.room.Dao
interface ProfileDao {

    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: androidx.webkit.Profile)

    @androidx.room.Update
    suspend fun updateProfile(profile: androidx.webkit.Profile)

    @androidx.room.Delete
    suspend fun deleteProfile(profile: androidx.webkit.Profile)

    @androidx.room.Query("SELECT * FROM profile WHERE id = :profileId")
    fun getProfileById(profileId: Int): Flow<androidx.webkit.Profile>

    @androidx.room.Query("SELECT * FROM profile")
    fun getAllProfiles(): Flow<List<androidx.webkit.Profile>>
}