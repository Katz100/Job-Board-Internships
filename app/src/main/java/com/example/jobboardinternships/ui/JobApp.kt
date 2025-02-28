package com.example.jobboardinternships.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.jobboardinternships.data.local.LocalJobDataProvider
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jobboardinternships.data.JobType

private const val TAG = "MainActivity"



@Composable
fun JobApp(
    modifier: Modifier = Modifier
) {
    val viewModel: JobViewModel = viewModel()
    val jobUiState = viewModel.uiState.collectAsState().value

    var isSearchActive by rememberSaveable { mutableStateOf(false) }

    JobList(jobList = jobUiState.jobPostings,
        onJobPostingClicked = {},
        onLeftArrowClicked = {},
        onRightArrowClicked = {},
        onQueryChange = {viewModel.updateSearchQuery(it)},
        isSearchActive = isSearchActive,
        onSearchActiveChange = {isSearchActive = it},
        searchQuery = jobUiState.searchQuery
        )
}



