package com.example.freeweights.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Regime::class,
            parentColumns = ["regimeId"],
            childColumns = ["regimeId"],
            onDelete = ForeignKey.CASCADE // Optional: Delete sets if the regime is deleted
        )
    ]
)
data class Set(@PrimaryKey(autoGenerate = true) val id: Int = 0,
               var reps: Int,
               val sets: Int,
               val loadType: String,
               val exerciseId: Int,
               val regimeId: Int,
    )
