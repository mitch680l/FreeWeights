package com.example.freeweights.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Profile(@PrimaryKey(autoGenerate = true) val id: Int = 0, val name: String,
              val weight: Int, val workoutsCompleted: Int, val goalWeight: Int)