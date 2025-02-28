package com.example.jobboardinternships.ui

import android.nfc.Tag
import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import com.example.jobboardinternships.data.Job


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
    modifier: Modifier = Modifier
) {

    Scaffold (
        topBar = {
            SearchBar(
                query = searchQuery,
                onQueryChange = { onQueryChange(it) },
                onSearch = { onSearchActiveChange(false) },
                active = isSearchActive,
                onActiveChange = { onSearchActiveChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
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