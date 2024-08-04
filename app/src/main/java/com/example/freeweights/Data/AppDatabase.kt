package com.example.freeweights.Data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Exercise::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao

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