package com.example.tailor.database

import com.example.tailor.model.user.BodyMeasurement
import com.example.tailor.model.user.Orders
import com.example.tailor.model.user.UserModel


interface ITailorDatabase {

    suspend fun registerUser(userModel:UserModel,userId:String): com.google.android.gms.tasks.Task<Void>

    suspend fun getUserProfile(userId : String) // return UserModel

    suspend fun updateUserProfile(email: String, userModel:Map<String,Any>,userId: String)

    suspend fun addOrder(orderModel: Orders)

    suspend fun getOrders()

    suspend fun deleteOrder(orderModel: Orders,docId: String)

    suspend fun addBodyMeasurement(bodyMeasurement: BodyMeasurement)

    suspend fun updateBodyMeasurement(bodyMeasurement:Map<String,Double>)
}