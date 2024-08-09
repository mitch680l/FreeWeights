package com.example.freeweights.data

import kotlinx.coroutines.flow.Flow

class ProfileRepository constructor(
    private val profileDao: ProfileDao
) {

    suspend fun insertProfile(profile: Profile) {
        profileDao.insertProfile(profile)
    }

    suspend fun updateProfile(profile: Profile) {
        profileDao.updateProfile(profile)
    }

    suspend fun deleteProfile(profile: Profile) {
        profileDao.deleteProfile(profile)
    }

    fun getAllProfiles(): Flow<List<Profile>> {
        return profileDao.getAllProfiles()
    }
}