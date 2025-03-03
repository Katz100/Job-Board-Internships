package com.example.jobboardinternships.ui

import androidx.lifecycle.ViewModel
import com.bumptech.glide.load.engine.executor.GlideExecutor.UncaughtThrowableStrategy.LOG
import com.example.jobboardinternships.data.Job
import com.example.jobboardinternships.data.JobScreen
import com.example.jobboardinternships.data.JobType
import com.example.jobboardinternships.data.local.LocalJobDataProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import android.util.Log

private const val TAG = "ViewModel"

class JobViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(JobUiState())
    val uiState: StateFlow<JobUiState> = _uiState

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        updateJobPostings(JobType.InOfficeAndRemote, "")
    }

    fun updateSearchQuery(newQuery: String) {
        _uiState.update {
            it.copy(
                searchQuery = newQuery
            )
        }
    }

    fun updateJobType(jobType: JobType) {
        _uiState.update {
            it.copy(jobType = jobType, isLoading = true)
        }
        updateJobPostings2(jobType, _uiState.value.searchQuery)
    }

    fun updateJobPostings2(jobType: JobType, query: String) {
        Log.d(TAG, "Postings2")
        val jobPostings: List<Job> = LocalJobDataProvider.newTestJobs // Get data from api

        _uiState.update {
            it.copy(
                jobPostings = jobPostings,
                isLoading = false
            )
        }
    }
    fun updateJobPostings(jobType: JobType, query: String) {
        val jobPostings: List<Job> = LocalJobDataProvider.testJobs // Get data from api

        _uiState.update {
            it.copy(
                jobPostings = jobPostings,
                isLoading = false
            )
        }
    }


    fun selectJob(job: Job) {
        _uiState.update {
            it.copy(
                currentSelectedJob = job,
                currentScreen = JobScreen.JobDetails)
        }
    }
}