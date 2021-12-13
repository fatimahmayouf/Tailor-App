package com.example.tailor.model.home


import com.google.gson.annotations.SerializedName

data class HomePhotoModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("url1")
    val url1: String,
    @SerializedName("url2")
    val url2: String,
    @SerializedName("url3")
    val url3: String
)