package com.example.jobboardinternships.ui

import android.widget.TextView
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.jobboardinternships.data.Job
import com.example.jobboardinternships.data.local.LocalJobDataProvider

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun JobDetails(
    modifier: Modifier = Modifier,
    job: Job,
    onBackPressed: () -> Unit
) {

    BackHandler {
        onBackPressed()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            GlideImage(
                model = job.organizationLogo,
                contentDescription = "Company Logo",
                modifier = modifier.size(100.dp),
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = job.title
                )
                Text(
                    text = job.organization
                )
            }
        }

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Apply Now"
            )
        }

        Text(
            text = job.locations
        )

        Card (
            modifier = modifier.padding(12.dp)
        ){
            Text(
                text = job.salary ?: "No Salary Disclosed",
                modifier = modifier.padding(6.dp)
            )
        }

        HorizontalDivider()

        AndroidView(
            modifier = modifier.padding(16.dp),
            factory = { context -> TextView(context) },
            update = { it.text = HtmlCompat.fromHtml(job.description, HtmlCompat.FROM_HTML_MODE_COMPACT) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun JobDetailsPreview() {
    JobDetails(job = LocalJobDataProvider.testJobs[0], onBackPressed = {})

}