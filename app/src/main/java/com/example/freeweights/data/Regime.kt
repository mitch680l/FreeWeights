package com.example.freeweights.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Regime(@PrimaryKey(autoGenerate = true) val regimeId: Int = 0,
                  val name: String,
                  val description: String,
                  val numberOfCompletion: Int)