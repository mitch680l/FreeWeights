package com.example.freeweights.data

class ProfileRepository @Inject constructor(
    private val profileDao: ProfileDao
) {

    suspend fun insertProfile(profile: androidx.webkit.Profile) {
        profileDao.insertProfile(profile)
    }

    suspend fun updateProfile(profile: androidx.webkit.Profile) {
        profileDao.updateProfile(profile)
    }

    suspend fun deleteProfile(profile: androidx.webkit.Profile) {
        profileDao.deleteProfile(profile)}

    fun getProfileById(profileId: Int): Flow<androidx.webkit.Profile> {
        return profileDao.getProfileById(profileId)
    }

    fun getAllProfiles(): Flow<List<androidx.webkit.Profile>> {
        return profileDao.getAllProfiles()
    }
}