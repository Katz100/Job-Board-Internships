package com.example.jobboardinternships.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.jobboardinternships.data.Job
import com.example.jobboardinternships.data.local.LocalJobDataProvider
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun JobPosting(
    job: Job,
    onJobClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onJobClicked,
        elevation = CardDefaults.cardElevation(10.dp),
        border = BorderStroke(2.dp, Color.Gray),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = modifier.padding(16.dp)
    ) {

        Text(text = "TEEESSSSSTTTT")
        GlideImage(model = job.organizationLogo, contentDescription = "ok")
    }
}

@Preview(showBackground = true)
@Composable
fun JobPostingPreview() {
    JobPosting(
        onJobClicked = { println("Clicked!") },
        job = LocalJobDataProvider.testJobs[0]
    )
}

fun formatDate(unformattedDate: String): String {
    val parsedDate = LocalDateTime.parse(unformattedDate)
    val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
    val formattedDate = parsedDate.format(formatter)
    return formattedDate
}
