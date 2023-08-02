package com.andyfan.hackathon

import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Headers

data class AvatarImageListApiResponse(val result: AvatarImageList)
data class AvatarImageList(val images: List<AvatarImage>)
data class AvatarImage(val image_id: Int, val request_path: String)

interface NetworkService {

    @Headers("accept: application/json")
    @GET("/v1/image/list")
    suspend fun getImageListApiResponse(): Response<AvatarImageListApiResponse>
}