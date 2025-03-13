package com.example.jobboardinternships.LocalDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface JobDao {
    @Query("SELECT * FROM SavedJobs")
    fun getAll(): LiveData<List<SavedJobs>>

    @Query("SELECT EXISTS (SELECT 1 FROM SavedJobs where uid = :uid)")
    suspend fun jobExists(uid: Int): Boolean

    @Delete
    suspend fun delete(job: SavedJobs)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(job: SavedJobs)
}