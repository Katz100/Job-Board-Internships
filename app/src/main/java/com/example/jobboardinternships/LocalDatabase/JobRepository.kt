package com.example.jobboardinternships.LocalDatabase

import android.app.Application
import androidx.lifecycle.LiveData

class JobRepository(application: Application) {
    private var jobDao: JobDao

    init {
        val database = JobDatabase.getDatabase(application)
        jobDao = database.jobDao()
    }

    val readAllJobs: LiveData<List<SavedJobs>> = jobDao.getAll()

    suspend fun insertJob(job: SavedJobs) {
        jobDao.insert(job)
    }

    suspend fun deleteJob(job: SavedJobs){
        jobDao.delete(job)
    }

    suspend fun jobExists(uid: Int): Boolean {
        return jobDao.jobExists(uid)
    }

}