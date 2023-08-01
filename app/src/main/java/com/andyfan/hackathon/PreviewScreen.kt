package com.andyfan.hackathon

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewScreen() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 30.dp, start = 10.dp, end = 10.dp, bottom = 30.dp)
            .verticalScroll(rememberScrollState()),
        shape = RoundedCornerShape(10.dp)
    ) {
        var videoUrl by remember {
            mutableStateOf("")
        }

        videoUrl = "https://andyfanfan.myds.me/kodexplorer/data/User/admin/home/%E8%A7%86%E9%A2%91/SampleVideo_1280x720_1mb.mp4"

        ConstraintLayout{
            val (title, video, nextButton) = createRefs()

            val guideLine = createGuidelineFromTop(0.1f)

            Text(text = "Preview",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(guideLine)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(video.top)
                }
            )

            VideoPlayer(
                videoUrl = videoUrl,
                modifier = Modifier
                    .constrainAs(video) {
                        top.linkTo(title.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(nextButton.bottom)
                    }
                    .height(400.dp)
            )

            Button(
                onClick = {  },
                modifier = Modifier.constrainAs(nextButton) {
                    top.linkTo(video.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                Text(text = "Download")
            }
        }
    }
}

@Composable
fun VideoPlayer(videoUrl: String, modifier: Modifier) {
    val context = LocalContext.current
    val exoPlayer = remember(context) {
        SimpleExoPlayer.Builder(context).build().apply {
            val dataMediaSourceFactory : DataSource.Factory = DefaultDataSourceFactory(context)
            Util.getUserAgent(context, context.packageName)

            val source = ProgressiveMediaSource.Factory(dataMediaSourceFactory).createMediaSource(
                Uri.parse(videoUrl))

            this.prepare(source)
        }
    }

    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
            }
        },
        modifier = modifier.fillMaxSize()
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewPreviewScreen() {
    PreviewScreen()
}