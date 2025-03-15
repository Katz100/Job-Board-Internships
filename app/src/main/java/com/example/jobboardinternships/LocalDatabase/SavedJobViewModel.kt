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

    val TAG = "SavedJob"
    fun fetchAllJobs(): LiveData<List<SavedJobs>> {
        jobRepository.readAllJobs.observeForever {jobs ->
            Log.d(TAG, "Job List: $jobs")
        }
        return jobRepository.readAllJobs
    }

    fun insertJob(job: Job) {
        viewModelScope.launch {
            jobRepository.insertJob(parseJob(job))
        }
    }

    fun deleteJob(job: Job) {
        viewModelScope.launch {
            jobRepository.deleteJob(parseJob(job))
        }
    }

    suspend fun jobExists(uid: Int): Boolean {
            return jobRepository.jobExists(uid)
    }

    private fun parseJob(job: Job): SavedJobs {
        val jobConvert: SavedJobs = SavedJobs(
            job.id.toInt(),
            job.title,
            job.organization,
            job.organizationLogo,
            job.organizationUrl,
            job.datePosted,
            job.locations,
            job.salary,
            job.description
        )
        Log.d(TAG, jobConvert.description.toString())
        return jobConvert
    }


}