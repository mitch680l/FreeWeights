package com.example.freeweights.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Exercise::class, Profile::class, Regime::class, Set::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
    abstract fun profileDao(): ProfileDao
    abstract fun workoutDao(): WorkoutDao


    companion object {
        private var Instance: AppDatabase? = null;

        fun getDatabase(context: Context): AppDatabase {
            Log.d("AppDatabase", "Database being created.")
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "app_database").fallbackToDestructiveMigration().build().also { Instance = it }

            }

        }
    }
}