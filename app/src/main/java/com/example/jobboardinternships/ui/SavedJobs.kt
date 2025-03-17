package com.example.jobboardinternships.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.example.jobboardinternships.LocalDatabase.SavedJobs
import com.example.jobboardinternships.data.Job

@Composable
fun UserSavedJobs(
    modifier: Modifier = Modifier,
    jobList: List<SavedJobs>,
    onJobPostingClicked: (Job) -> Unit,
    onBackPressed: () -> Unit
) {
    val state = rememberLazyListState()

    BackHandler {
        onBackPressed()
    }

    if (jobList.isEmpty()) {
        Text(
            text = "You do not have any saved jobs.",
            textAlign = TextAlign.Center,
            color = Color.Gray,
            modifier = Modifier.wrapContentHeight()
        )
    } else {
        LazyColumn(
            state = state,
            modifier = modifier
                .fillMaxSize()
        ) {
            items(jobList.size) { index ->

                val job = jobList[index]
                JobPosting(
                    job = parseSavedJob(job),
                    onJobClicked = { onJobPostingClicked(parseSavedJob(job)) })
            }
        }
    }
}

fun parseSavedJob(job: SavedJobs): Job {
    val jobConvert: Job = Job(
        job.uid.toString(),
        job.title.toString(),
        job.organization.toString(),
        job.organizationLogo,
        job.organizationUrl.toString(),
        job.datePosted.toString(),
        job.locations.toString(),
        job.salary,
        job.description.toString()
    )

    return jobConvert
}