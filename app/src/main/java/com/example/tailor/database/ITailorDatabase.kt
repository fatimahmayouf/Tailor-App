package com.example.tailor.database

import com.example.tailor.model.user.BodyMeasurement
import com.example.tailor.model.user.Orders
import com.example.tailor.model.user.UserModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot


interface ITailorDatabase {

    suspend fun registerUser(userModel:UserModel,userId:String): com.google.android.gms.tasks.Task<Void>

    suspend fun getUserProfile(userId : String):Task<DocumentSnapshot> // return UserModel

    suspend fun updateUserProfile(userModel:Map<String,Any>,userId: String):Task<Void>

    suspend fun addOrder(orderModel: Orders): Task<Void>

    suspend fun getOrders():Task<QuerySnapshot>

    suspend fun deleteOrder(docId:String):Task<Void>

    suspend fun addBodyMeasurement(bodyMeasurement: BodyMeasurement):Task<Void>

    suspend fun getBodyMeasurement(userId: String):Task<DocumentSnapshot>

    suspend fun updateBodyMeasurement(bodyMeasurement:Map<String,Double>)
}