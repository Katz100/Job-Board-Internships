package com.example.jobboardinternships.ui

import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jobboardinternships.data.Job
import com.example.jobboardinternships.data.local.LocalJobDataProvider
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun JobPosting(
    job: Job,
    modifier: Modifier = Modifier
) {
    Card {

    }
}

@Preview(showBackground = true)
@Composable
fun JobPostingPreview() {
    JobPosting(
        job = LocalJobDataProvider.testJobs[0]
    )
}

fun formatDate(unformattedDate: String): String {
    val parsedDate = LocalDateTime.parse(unformattedDate)
    val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
    val formattedDate = parsedDate.format(formatter)
    return formattedDate
}
