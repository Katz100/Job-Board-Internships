package com.example.jobboardinternships.LocalDatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SavedJobViewModel(applicationObject: Application): AndroidViewModel(applicationObject) {
    private val jobRepository: JobRepository = JobRepository(applicationObject)
    fun fetchAllJobs(): LiveData<List<SavedJobs>> {
        return jobRepository.readAllJobs
    }

    fun insertJob(job: SavedJobs) {
        viewModelScope.launch {
            jobRepository.insertJob(job)
        }
    }

    fun deleteJob(job: SavedJobs) {
        viewModelScope.launch {
            jobRepository.deleteJob(job)
        }
    }
}