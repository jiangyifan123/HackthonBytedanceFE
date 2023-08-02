package com.andyfan.hackathon

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun DemoScreen(
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 30.dp, start = 10.dp, end = 10.dp, bottom = 30.dp)
            .verticalScroll(rememberScrollState()),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Demo",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp)
            )
            PhotoList(navController)
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun PhotoList(navController: NavController) {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://michaelwu.duckdns.org:51320")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api = retrofit.create(NetworkService::class.java)

    var imageList by remember { mutableStateOf(emptyList<AvatarImage>()) }

    LaunchedEffect(Unit) {
        imageList = fetchPhotoList(api)
    }

    imageList.forEach {
        Image(
            painter = rememberImagePainter("http://michaelwu.duckdns.org:51320${it.request_path}"),
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxWidth()
                .size(250.dp)
                .padding(10.dp)
                .clickable {
                    getVideoByPhoto(photoId = it.image_id, navController = navController)
                }
        )
    }
}

fun getVideoByPhoto(photoId: Int, navController: NavController) {
    Store.photoId = photoId
    Log.d("Yifan.debug", "request photoId: $photoId, message: ${Store.message}")
    navController.navigate(route = Screen.Preview.router)
}

suspend fun fetchPhotoList(api: NetworkService): List<AvatarImage> {
    val response: Response<AvatarImageListApiResponse> = api.getImageListApiResponse()
    return response.body()!!.result.images
}

@Composable
@Preview(showBackground = true)
fun PreviewDemoScreen() {
    DemoScreen(navController = rememberNavController())
}