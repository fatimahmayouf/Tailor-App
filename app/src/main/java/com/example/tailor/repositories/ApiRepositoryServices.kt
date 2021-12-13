package com.example.tailor.repositories

import android.annotation.SuppressLint
import android.content.Context
import com.example.tailor.api.MockHomeApi
import com.example.tailor.api.PexelsExploreApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

private const val BASE_URL = "https://api.pexels.com/v1/"
private const val BASE_URL2 = "https://61af59bd3e2aba0017c49217.mockapi.io/"

class ApiRepositoryServices(val context: Context){

        private val retrofitService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val retrofitService2 = Retrofit.Builder()
        .baseUrl(BASE_URL2)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

        private val retrofitApi = retrofitService.create(PexelsExploreApi::class.java)
        private val retrofitApi2 = retrofitService2.create(MockHomeApi::class.java)

        suspend fun getExplorePhoto()= retrofitApi.getProductExpolre()
        suspend fun getHomePhoto()= retrofitApi2.getHomeProduct()

       companion object{
            @SuppressLint("StaticFieldLeak")
            private var instance: ApiRepositoryServices? = null
            fun init(context: Context){
                if(instance == null)
                    instance = ApiRepositoryServices(context)
            }
            fun get(): ApiRepositoryServices{
                return instance ?: throw Exception("ApiServicesRepository must be initialized")
            }
            }
        }
