package com.example.tailor.api

import com.example.tailor.model.home.HomePhotoModel
import retrofit2.Response
import retrofit2.http.GET

interface MockHomeApi {

    @GET("models")
    suspend fun getHomeProduct(): Response<List<HomePhotoModel>>
}