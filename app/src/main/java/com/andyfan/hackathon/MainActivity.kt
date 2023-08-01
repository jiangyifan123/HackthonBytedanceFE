package com.andyfan.hackathon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.andyfan.hackathon.ui.theme.HackathonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val downloader = AndroidDownloader(this)
//            downloader.downloadFile("https://andyfanfan.myds.me/kodexplorer/data/User/admin/home/%E8%A7%86%E9%A2%91/SampleVideo_1280x720_1mb.mp4")
            HackathonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DownloadScreen()
                }
            }
        }
    }
}