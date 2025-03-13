package com.example.jobboardinternships.LocalDatabase

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.jobboardinternships.data.Job
import kotlinx.coroutines.launch


class SavedJobViewModel(applicationObject: Application): AndroidViewModel(applicationObject) {
    private val jobRepository: JobRepository = JobRepository(applicationObject)
    fun fetchAllJobs(): LiveData<List<SavedJobs>> {
        return jobRepository.readAllJobs
    }
    val TAG = "SavedJob"
    fun insertJob(job: Job) {
        viewModelScope.launch {
            val jobConvert: SavedJobs = SavedJobs(
                job.id.toInt(),
                job.title,
                job.organization,
                job.organizationLogo,
                job.organizationUrl,
                job.datePosted,
                job.locations,
                job.salary,
                ""
            )

            jobRepository.insertJob(jobConvert)
        }
    }

    fun deleteJob(job: Job) {
        viewModelScope.launch {
            val jobConvert: SavedJobs = SavedJobs(
                job.id.toInt(),
                job.title,
                job.organization,
                job.organizationLogo,
                job.organizationUrl,
                job.datePosted,
                job.locations,
                job.salary,
                ""
            )
            jobRepository.deleteJob(jobConvert)
        }
    }

    suspend fun jobExists(uid: Int): Boolean {
            return jobRepository.jobExists(uid)
    }
}