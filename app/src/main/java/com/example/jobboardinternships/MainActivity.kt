package com.example.jobboardinternships

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jobboardinternships.ui.theme.JobBoardInternshipsTheme
import com.example.jobboardinternships.ui.JobApp
import okhttp3.OkHttpClient
import okhttp3.Request
import android.util.Log
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.jobboardinternships.LocalDatabase.JobDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "MainApp"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = JobDatabase.getDatabase(this.applicationContext)
        enableEdgeToEdge()
        setContent {

            Scaffold { scaffoldPadding ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(scaffoldPadding)
                ) {
                    JobApp(db)
                }
            }
            }
        }
    }




