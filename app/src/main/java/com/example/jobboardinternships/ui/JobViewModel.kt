package com.example.jobboardinternships.ui

import androidx.lifecycle.ViewModel
import com.example.jobboardinternships.data.Job
import com.example.jobboardinternships.data.JobType
import com.example.jobboardinternships.data.local.LocalJobDataProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

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
        updateJobPostings(_uiState.value.jobType, newQuery)
    }

    fun updateJobType(jobType: JobType) {
        _uiState.update {
            it.copy(jobType = jobType, isLoading = true)
        }
        updateJobPostings(jobType, _uiState.value.searchQuery)
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
            it.copy(currentSelectedJob = job)
        }
    }
}