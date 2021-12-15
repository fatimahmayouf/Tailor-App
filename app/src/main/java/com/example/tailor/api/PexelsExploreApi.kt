package com.example.tailor.api

import com.example.tailor.model.explore.ExplorePhotoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

private const val API_KEY = "563492ad6f91700001000001a2cf5b46ce8e4d07b9424a2e6a9c2c3c"

interface PexelsExploreApi {

    @GET("search")
    suspend fun getProductExpolre(
        @Header("Authorization") key: String = API_KEY,
        @Query("query") searchWords: String = "Fashion queen"

    ): Response<ExplorePhotoModel>
}