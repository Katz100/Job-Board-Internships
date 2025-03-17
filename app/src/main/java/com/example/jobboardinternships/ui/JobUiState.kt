package com.example.jobboardinternships.ui

import com.example.jobboardinternships.data.Job
import com.example.jobboardinternships.data.JobScreen
import com.example.jobboardinternships.data.JobType

data class JobUiState(
    val jobType: JobType = JobType.InOfficeAndRemote,
    val jobPostings: List<Job> = emptyList(),
    val searchQuery: String = "",
    val locationQuery: String = "",
    val isLoading: Boolean = false,
    val currentSelectedJob: Job? = null,
    val currentScreen: JobScreen = JobScreen.Home,
    val collapseColumn: Boolean = false,
    val offset: Int = 0,
    val remoteSelected: Boolean = false,
    val inPersonSelected: Boolean = false,
    val viewingSavedJobs: Boolean = false
) {
}

