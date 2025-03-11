package com.example.jobboardinternships.LocalDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SavedJobs::class], version = 1)
abstract class JobDatabase: RoomDatabase() {
    abstract fun jobDao(): JobDao

    companion object {
        @Volatile
        private var INSTANCE: JobDatabase? = null

        fun getDatabase(context: Context): JobDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, JobDatabase::class.java, "job-database")
                    .fallbackToDestructiveMigrationFrom()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}