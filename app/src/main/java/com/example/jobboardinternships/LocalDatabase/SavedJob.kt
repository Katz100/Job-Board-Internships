package com.example.jobboardinternships.LocalDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedJobs(
    @PrimaryKey val uid: Int,
    val title: String?,
    val organization: String?,
    val organizationLogo: String?,
    val organizationUrl: String?,
    val datePosted: String?,
    val locations: String?,
    val salary: String?,
    val description: String?
)
