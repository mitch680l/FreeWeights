package com.example.freeweights.data


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exercise(@PrimaryKey(autoGenerate = true) val id: Int = 0,
               val name: String,
               val muscleGroup: String,
               val type: String,
               val description: String,
               var max: Int
)