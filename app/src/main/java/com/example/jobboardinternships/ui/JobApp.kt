package com.example.jobboardinternships.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.jobboardinternships.data.local.LocalJobDataProvider

@Composable
fun JobApp(
    modifier: Modifier = Modifier
) {
    JobPosting(job = LocalJobDataProvider.testJobs[0], onJobClicked = { /*TODO*/ })
}
