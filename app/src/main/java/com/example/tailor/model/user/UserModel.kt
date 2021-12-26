package com.example.tailor.model.user

data class UserModel(
    var fullName: String,
    var email: String,
    var location: String = "location",
    var phoneNumber: Int

)
