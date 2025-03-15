package com.example.jobboardinternships.ui

import androidx.lifecycle.ViewModel
import com.bumptech.glide.load.engine.executor.GlideExecutor.UncaughtThrowableStrategy.LOG
import com.example.jobboardinternships.data.Job
import com.example.jobboardinternships.data.JobScreen
import com.example.jobboardinternships.data.JobType
import com.example.jobboardinternships.data.local.LocalJobDataProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.jobboardinternships.LocalDatabase.JobDatabase
import com.example.jobboardinternships.LocalDatabase.SavedJobs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray

private const val TAG = "ViewModel"

class JobViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(JobUiState())
    val uiState: StateFlow<JobUiState> = _uiState

    private val client = OkHttpClient()

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        updateJobPostings(JobType.InOfficeAndRemote, "")
    }

    fun changeRemoteSelection() {

        _uiState.update {
            it.copy(
                remoteSelected = !_uiState.value.remoteSelected,
            )
        }

        val remoteSelected: Boolean = _uiState.value.remoteSelected
        val inPersonSelected: Boolean = _uiState.value.inPersonSelected
        val jobType = if (remoteSelected && inPersonSelected) {
            JobType.InOfficeAndRemote
        } else if (!remoteSelected && inPersonSelected) {
            JobType.InOffice
        } else if (remoteSelected && !inPersonSelected) {
            JobType.Remote
        } else {
            JobType.InOfficeAndRemote
        }

        _uiState.update {
            it.copy(
                jobType = jobType,
            )
        }
    }

    fun changeInPersonSelection() {

        _uiState.update {
            it.copy(
                inPersonSelected = !_uiState.value.inPersonSelected,
            )
        }

        val remoteSelected: Boolean = _uiState.value.remoteSelected
        val inPersonSelected: Boolean = _uiState.value.inPersonSelected
        val jobType = if (remoteSelected && inPersonSelected) {
            JobType.InOfficeAndRemote
        } else if (!remoteSelected && inPersonSelected) {
            JobType.InOffice
        } else if (remoteSelected && !inPersonSelected) {
            JobType.Remote
        } else {
            JobType.InOfficeAndRemote
        }

        _uiState.update {
            it.copy(
                jobType = jobType,
            )
        }
    }

    fun collapseColumn() {
        _uiState.update {
            it.copy(
                collapseColumn = true
            )
        }
    }

    fun expandColumn() {
        _uiState.update {
            it.copy(
                collapseColumn = false
            )
        }
    }

    fun increaseOffset() {
        if (_uiState.value.jobPostings.size == 10) {
            val oldOffset: Int = _uiState.value.offset
            _uiState.update {
                it.copy(
                    offset = oldOffset + 10
                )
            }
            updateJobPostings(_uiState.value.jobType, _uiState.value.searchQuery)
        }
    }

    fun decreaseOffset() {
        if (_uiState.value.offset >= 10) {
            val oldOffset: Int = _uiState.value.offset
            _uiState.update {
                it.copy(
                    offset = oldOffset - 10
                )
            }
            updateJobPostings(_uiState.value.jobType, _uiState.value.searchQuery)
        }
    }
    fun updateSearchQuery(newQuery: String) {
        _uiState.update {
            it.copy(
                searchQuery = newQuery
            )
        }
    }

    fun updateSearchLocationQuery(newQuery: String) {
        _uiState.update {
            it.copy(
                locationQuery = newQuery
            )
        }
    }

    fun updateJobType(jobType: JobType) {
        _uiState.update {
            it.copy(jobType = jobType, isLoading = true)
        }
        updateJobPostings(jobType, _uiState.value.searchQuery)
    }

    fun updateJobPostings2(jobType: JobType, query: String) {
        val jobPostings: List<Job> = LocalJobDataProvider.newTestJobs // Get data from api

        _uiState.update {
            it.copy(
                jobPostings = jobPostings,
                isLoading = false
            )
        }
    }
    fun updateJobPostings(jobType: JobType, query: String) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d(TAG, jobType.name)
                val request = Request.Builder()
                    .url("https://internships-api.p.rapidapi.com/active-ats-7d?title_filter=$query&remote=${
                        when(jobType) {
                            JobType.InOfficeAndRemote -> ""
                            JobType.Remote -> "true"
                            JobType.InOffice -> "false"
                        }
                    }&location_filter=${_uiState.value.locationQuery}&offset=${_uiState.value.offset}" +
                            "&description_type=html")
                    .get()
                    .addHeader("x-rapidapi-key", api_key)
                    .addHeader("x-rapidapi-host", "internships-api.p.rapidapi.com")
                    .build()

                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                Log.d(TAG, responseBody.toString())

                val jobPostings = parseJobsFromResponse(responseBody)
                Log.d(TAG, jobPostings.toString())
                _uiState.update {
                    it.copy(
                        jobPostings = jobPostings,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching jobs: ${e.message}")
                _uiState.update { it.copy(isLoading = false) }
            }
        }
        /*
        val jobPostings: List<Job> = LocalJobDataProvider.testJobs // Get data from api

        _uiState.update {
            it.copy(
                jobPostings = jobPostings,
                isLoading = false
            )
        }

         */
    }

    private fun parseJobsFromResponse(response: String?): List<Job> {
        val jobs = mutableListOf<Job>()
        if (!response.isNullOrEmpty()) {
            val jsonArray = JSONArray(response)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val locationsArray = jsonObject.optJSONArray("locations_derived")
                val location = if (locationsArray != null && locationsArray.length() > 0) {
                    locationsArray.optString(0)
                } else {
                    ""
                }

                val salaryObject = jsonObject.optJSONObject("salary_raw")
                val valueObject = salaryObject?.optJSONObject("value")

                val minSalary = valueObject?.optInt("minValue", -1) ?: -1
                val maxSalary = valueObject?.optInt("maxValue", -1) ?: -1
                val currency = salaryObject?.optString("currency", "USD") ?: "USD"
                val unit = valueObject?.optString("unitText", "YEAR") ?: "YEAR"

                val salary: String = when {
                    minSalary > 0 && maxSalary > 0 -> "$$minSalary - $$maxSalary $unit ($currency)"
                    minSalary > 0 -> "$$minSalary $unit ($currency)"
                    maxSalary > 0 -> "$$maxSalary $unit ($currency)"
                    else -> "Salary Not Provided"
                }

                val job = Job(
                    id = jsonObject.optString("id", ""),
                    datePosted = jsonObject.optString("date_posted", ""),
                    locations = location,
                    organizationLogo = jsonObject.optString("organization_logo", ""),
                    organization = jsonObject.optString("organization", ""),
                    organizationUrl = jsonObject.optString("url", ""),
                    salary = salary,
                    title = jsonObject.optString("title"),
                    description = jsonObject.optString("description_html")
                )
                jobs.add(job)
                Log.d(TAG, "Job: ${job.toString()}")
            }
        }
        return jobs
    }

    fun selectJob(job: Job) {
        _uiState.update {
            it.copy(
                currentSelectedJob = job,
                currentScreen = JobScreen.JobDetails)
        }
    }
    fun goBackToHome() {
        _uiState.update {
            it.copy(
                currentScreen = JobScreen.Home)
        }
    }

    fun goToSavedJobs() {
        _uiState.update {
            it.copy(
                currentScreen = JobScreen.SavedJobs
            )
        }

        Log.d(TAG, _uiState.value.currentScreen.toString())
    }

}