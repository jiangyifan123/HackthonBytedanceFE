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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun DemoScreen(
    navController: NavController
) {
    val photoList = listOf<photoItems>(
        photoItems(R.drawable.hitler, 1, "Hitler"),
        photoItems(R.drawable.mona_lisa, 2, "Mona Lisa"),
        photoItems(R.drawable.mona_lisa, 2, "Mona Lisa"),
        photoItems(R.drawable.mona_lisa, 2, "Mona Lisa"),
        photoItems(R.drawable.mona_lisa, 2, "Mona Lisa"),
        photoItems(R.drawable.mona_lisa, 2, "Mona Lisa"),
    )

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

            PhotoList(photoList, navController)
        }
    }
}

@Composable
fun PhotoList(photos: List<photoItems>, navController: NavController) {
    photos.forEach {
        Image(
            painter = painterResource(id = it.id),
            contentDescription = it.name,
            modifier = Modifier
                .fillMaxWidth()
                .size(250.dp)
                .padding(10.dp)
                .clickable {
                    getVideoByPhoto(photoId = it.photoId, navController = navController)
                }
        )
        Text(
            text = it.name,
            fontWeight = FontWeight.Bold
        )
    }
}

fun getVideoByPhoto(photoId: Int, navController: NavController) {
    Store.photoId = photoId
    Log.d("Yifan.debug", "request photoId: $photoId, message: ${Store.message}")
    navController.navigate(route = Screen.Preview.router)
}

data class photoItems(
    val id: Int = 0,
    val photoId: Int = 0,
    val name: String = ""
)

@Composable
@Preview(showBackground = true)
fun PreviewDemoScreen() {
    DemoScreen(navController = rememberNavController())
}