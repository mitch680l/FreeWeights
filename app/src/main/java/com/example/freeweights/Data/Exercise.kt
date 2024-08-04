package com.example.freeweights.Data


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Exercise(@PrimaryKey(autoGenerate = true) val id: Int = 0,
               val name: String,
    val muscleGroup: String,
    val type: String,
    val description: String,
    val max: Int
)