package com.example.jobboardinternships.data.local

import com.example.jobboardinternships.data.Job


object LocalJobDataProvider {
    val testJobs = listOf(
        Job(
            title = "Software Developer",
            organization = "Intel",
            organizationLogo = "https://assets.phenompeople.com/CareerConnectResources/pp/PGBPGNGLOBAL/images/job_logo_config-1678365337336.jpg",
            datePosted = "2025-02-20T00:00:00",
            locations = "Folsom, CA, US",
            salary = "24/hr = 30/hr",
            description = "Develop stuff"
        ),
        Job(
            title = "Software Developer 2",
            organization = "IBM",
            organizationLogo = "https://assets.phenompeople.com/CareerConnectResources/pp/PGBPGNGLOBAL/images/job_logo_config-1678365337336.jpg",
            datePosted = "2025-02-20T00:00:00",
            locations = "Sacramento, CA, US",
            salary = "24/hr = 30/hr",
            description = "Develop more stuff"
        )
    )
}