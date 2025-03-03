package com.example.jobboardinternships.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.jobboardinternships.data.local.LocalJobDataProvider
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jobboardinternships.data.Job
import com.example.jobboardinternships.data.JobScreen
import com.example.jobboardinternships.data.JobType

private const val TAG = "MainApp"



@Composable
fun JobApp(
    modifier: Modifier = Modifier
) {
    val viewModel: JobViewModel = viewModel()
    val jobUiState = viewModel.uiState.collectAsState().value

    var isSearchActive by rememberSaveable { mutableStateOf(false) }

    if (jobUiState.currentScreen == JobScreen.Home) {
        JobList(
            jobList = jobUiState.jobPostings,
            onJobPostingClicked = { job: Job ->
                viewModel.selectJob(job)

            },
            onLeftArrowClicked = {},
            onRightArrowClicked = {},
            onQueryChange = { viewModel.updateSearchQuery(it) },
            isSearchActive = isSearchActive,
            onSearchActiveChange = { isSearchActive = it },
            onSearch = { viewModel.updateJobPostings2(JobType.InOfficeAndRemote, it) },
            searchQuery = jobUiState.searchQuery,
            onSearchLocationQueryChange = {},
            onSearchLocationActiveChange = {},
            searchLocationQuery = "",
            isSearchLocationActive = false,
        )
    } else {
        jobUiState.currentSelectedJob?.let {
            JobDetails(
                job = it
            )
        }
    }
}



