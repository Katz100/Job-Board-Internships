package com.example.jobboardinternships.ui

import android.nfc.Tag
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jobboardinternships.R
import com.example.jobboardinternships.data.Job
import com.example.jobboardinternships.data.local.LocalJobDataProvider


private const val TAG = "MainActivity"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobList(
    jobList: List<Job>,
    onJobPostingClicked: (Job) -> Unit,
    onLeftArrowClicked: () -> Unit,
    onRightArrowClicked: () -> Unit,
    searchQuery: String,
    isSearchActive: Boolean,
    onQueryChange: (String) -> Unit,
    onSearchActiveChange: (Boolean) -> Unit,
    searchLocationQuery: String,
    onSearchLocationQueryChange: (String) -> Unit,
    isSearchLocationActive: Boolean,
    onSearchLocationActiveChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold (
        topBar = {
            Column (
                modifier = Modifier.fillMaxWidth()
            ) {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { onQueryChange(it) },
                    onSearch = { onSearchActiveChange(false) },
                    active = isSearchActive,
                    onActiveChange = { onSearchActiveChange(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    placeholder = { Text("Search...") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search")
                    }
                ) {
                    if (isSearchActive) {
                        //TODO: Add suggestions
                        Text("Search Results for: $searchQuery", modifier = Modifier.padding(16.dp))
                    }
            }
                SearchBar(
                    query = searchLocationQuery,
                    onQueryChange = { onSearchLocationQueryChange(it) },
                    onSearch = { onSearchLocationActiveChange(false) },
                    active = isSearchLocationActive,
                    onActiveChange = { onSearchLocationActiveChange(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    placeholder = { Text("Location") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search")
                    }
                ) {
                    if (isSearchLocationActive) {
                        //TODO: Add suggestions
                        Text("Search Results for: $searchQuery", modifier = Modifier.padding(16.dp))
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, bottom = 8.dp)
                        .horizontalScroll(rememberScrollState())
                ) {
                    Card(
                        onClick = {},
                        modifier = Modifier
                    ) {
                        Text(
                            text = stringResource(R.string.remote_job),
                            modifier = Modifier
                                .padding(6.dp)
                        )
                    }
                    Card(
                        onClick = {},
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.in_person_job),
                            modifier = Modifier
                                .padding(6.dp)
                        )
                    }
                }

                }

        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(80.dp),
                content = {
                    IconButton(
                        onClick = { onLeftArrowClicked() }
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Previous Job Posting")
                    }

                    Spacer(modifier = Modifier.weight(1f))


                    IconButton(
                        onClick = { onRightArrowClicked() }
                    ) {
                        Icon(Icons.Filled.ArrowForward, contentDescription = "Next Job Posting")
                    }
                }
            )
        },
    ) { innerPadding ->
        val state = rememberLazyListState()
        LazyColumn(
            state = state,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(jobList.size) { index ->
                val job = jobList[index]
                JobPosting(
                    job = job,
                    onJobClicked = { onJobPostingClicked(job) })
            }
        }
    }
}

@Preview
@Composable
fun JobListingPreview() {
    JobList(
        jobList = LocalJobDataProvider.testJobs,
        onJobPostingClicked = {} ,
        onLeftArrowClicked = { /*TODO*/ },
        onRightArrowClicked = { /*TODO*/ },
        searchQuery = "",
        isSearchActive = false,
        onQueryChange = {} ,
        onSearchActiveChange = {},
        isSearchLocationActive = false,
        searchLocationQuery = "",
        onSearchLocationQueryChange = {},
        onSearchLocationActiveChange = {}
    )
}
