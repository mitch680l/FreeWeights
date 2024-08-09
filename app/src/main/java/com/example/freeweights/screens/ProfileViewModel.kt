package com.example.freeweights.screens

import com.example.freeweights.data.ProfileRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freeweights.data.Exercise
import com.example.freeweights.data.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileRepository: ProfileRepository) : ViewModel() {
    private val _profiles = MutableStateFlow<List<Profile>>(emptyList())
    val profiles: StateFlow<List<Profile>> = _profiles.asStateFlow()

    init {
        viewModelScope.launch {
            profileRepository.getAllProfiles().collect { profiles ->
                _profiles.value = profiles
            }
        }
    }

    fun addProfile(profile: Profile) {
        viewModelScope.launch {
            profileRepository.insertProfile(profile)
            _profiles.value += profile
        }
    }
}