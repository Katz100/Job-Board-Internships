package com.example.jobboardinternships.data

data class Job(
    val title: String,
    val organization: String,
    val organizationLogo: String?,
    val datePosted: String,
    val locations: String,
    val salary: String?,
    val description: String
    ) {

}
