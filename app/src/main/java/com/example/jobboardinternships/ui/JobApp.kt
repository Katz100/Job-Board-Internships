package com.example.jobboardinternships.ui

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.jobboardinternships.data.local.LocalJobDataProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalUriHandler
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jobboardinternships.LocalDatabase.JobDatabase
import com.example.jobboardinternships.LocalDatabase.SavedJobViewModel
import com.example.jobboardinternships.LocalDatabase.SavedJobs
import com.example.jobboardinternships.data.Job
import com.example.jobboardinternships.data.JobScreen
import com.example.jobboardinternships.data.JobType
import kotlinx.coroutines.launch

private const val TAG = "MainApp"



@Composable
fun JobApp(
    db: SavedJobViewModel,
    modifier: Modifier = Modifier
) {
    val viewModel: JobViewModel = viewModel()
    val jobUiState = viewModel.uiState.collectAsState().value
    val coroutineScope = rememberCoroutineScope()
    var isSearchActive by rememberSaveable { mutableStateOf(false) }
    var isSearchLocationActive by rememberSaveable { mutableStateOf(false) }

    if (jobUiState.currentScreen == JobScreen.Home) {
        JobList(
            jobList = jobUiState.jobPostings,
            onJobPostingClicked = { job ->
                viewModel.selectJob(job)
                //db.insertJob(job)
                Log.d(TAG, db.fetchAllJobs().value.toString())
                                  },
            onLeftArrowClicked = {viewModel.decreaseOffset()},
            onRightArrowClicked = {viewModel.increaseOffset()},
            onQueryChange = { viewModel.updateSearchQuery(it) },
            isSearchActive = isSearchActive,
            onSearchActiveChange = { isSearchActive = it },
            onSearch = { viewModel.updateJobPostings(jobUiState.jobType, it) },
            searchQuery = jobUiState.searchQuery,
            onSearchLocationQueryChange = {viewModel.updateSearchLocationQuery(it)},
            onSearchLocationActiveChange = {isSearchLocationActive = it},
            searchLocationQuery = jobUiState.locationQuery,
            onSearchLocation = {viewModel.updateJobPostings(jobUiState.jobType, jobUiState.searchQuery)},
            isSearchLocationActive = isSearchLocationActive,
            onCollapseColumnClicked = { viewModel.collapseColumn() },
            onExpandColumnIconClicked = { viewModel.expandColumn() },
            collapseColumn = jobUiState.collapseColumn,
            isLoading = jobUiState.isLoading,
            remoteSelected = jobUiState.remoteSelected,
            inPersonSelected = jobUiState.inPersonSelected,
            inPersonCardClick = { viewModel.changeInPersonSelection() },
            remoteCardClick = { viewModel.changeRemoteSelection() }
        )
    } else {
        jobUiState.currentSelectedJob?.let {
            val uriHandler = LocalUriHandler.current
            var isSaved by rememberSaveable { mutableStateOf(false) }

            LaunchedEffect(it.id) {
                isSaved = db.jobExists(it.id.toInt())
            }

            JobDetails(
                job = it,
                onBackPressed = { viewModel.goBackToHome() },
                onJobApplyButtonClick = { url ->
                    Log.d(TAG, url)
                    uriHandler.openUri(url)},
                onSaveButtonClick = {
                    if (isSaved) {
                        isSaved = false
                        db.deleteJob(it)
                    } else {
                        isSaved = true
                        db.insertJob(it)
                    }
                },
                isSaved = isSaved
            )
        } ?: Text(text = "Job Not Available")
    }
}



