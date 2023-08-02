package com.andyfan.hackathon

import android.app.DownloadManager
import android.net.Uri
import android.util.Log
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
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.DataSpec.HTTP_METHOD_POST
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.gson.Gson
import okio.ByteString.Companion.encode
import java.net.URLEncoder

data class GenerateVideoBody(val text: String, val image_id: Int)

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

//        Store.videoUrl = "https://vod-progressive.akamaized.net/exp=1690901002~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F2671%2F7%2F188355959%2F625866489.mp4~hmac=d69aac4835cffcaa6d72b2a81ceaaf1b28d28e6dc7f6ea81b6c79303787e8e6b/vimeo-prod-skyfire-std-us/01/2671/7/188355959/625866489.mp4"
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
                videoUrl = Store.videoUrl,
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
                onClick = {
                    Log.d("Yifan.debug", "downloading ${Store.videoUrl}")
                    Utils.downloader.downloadFile("http://michaelwu.duckdns.org:51320/v1/text-to-lipsync?image_id=${Store.photoId}&text=${URLEncoder.encode(Store.message, "UTF-8")}")
                },
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
                Uri.parse("http://michaelwu.duckdns.org:51320/v1/text-to-lipsync?image_id=${Store.photoId}&text=${URLEncoder.encode(Store.message, "UTF-8")}"))

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