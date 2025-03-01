package com.example.jobboardinternships.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.jobboardinternships.data.Job

@Composable
fun JobDetails(
    modifier: Modifier = Modifier,
    job: Job
) {
    Text(text = job.title)
}