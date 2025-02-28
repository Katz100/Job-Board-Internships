package com.example.jobboardinternships.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.Pager
import com.example.jobboardinternships.data.Job

@Composable
fun JobList(
    jobList: List<Job>,
    onJobPostingClicked: (Job) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold (
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(80.dp),
                content = {
                    IconButton(
                        onClick = { /* Handle Left Arrow Click */ }
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Previous Job Posting")
                    }

                    Spacer(modifier = Modifier.weight(1f))


                    IconButton(
                        onClick = { /* Handle Right Arrow Click */ }
                    ) {
                        Icon(Icons.Filled.ArrowForward, contentDescription = "Next Job Posting")
                    }
                }
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier.fillMaxSize().padding(innerPadding)
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