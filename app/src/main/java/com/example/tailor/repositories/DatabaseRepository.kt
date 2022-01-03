package com.example.tailor.repositories

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.tailor.database.ITailorDatabase
import com.example.tailor.database.TailorDataBase
import com.example.tailor.model.user.BodyMeasurement
import com.example.tailor.model.user.Orders
import com.example.tailor.model.user.UserModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.UploadTask
import java.io.File
import java.lang.Exception
import java.util.*

private const val TAG = "DatabaseRepository"
class DatabaseRepository(context: Context):ITailorDatabase{

    override suspend fun registerUser(
        userModel: UserModel,
        userId: String
    ): Task<Void> {
        val task = TailorDataBase.Usercollection
            .document(TailorDataBase.firebaseAuth.currentUser!!.uid).set(userModel)
        return task
    }

    override suspend fun getUserProfile(userId: String):Task<DocumentSnapshot>{
        val task = TailorDataBase.Usercollection.document(userId).get()
            return task
    }
    override suspend fun addOrder(orderModel: Orders):Task<Void> {
        val task =TailorDataBase.Usercollection
            .document(TailorDataBase.firebaseAuth.currentUser!!.uid).collection("Orders")
            .document().set(orderModel)
                return task
    }

    override suspend fun getOrders() :Task<QuerySnapshot>{
        val task =TailorDataBase.Usercollection
            .document(TailorDataBase.firebaseAuth.currentUser!!.uid).collection("Orders")
            .get()
        return  task
    }

    override suspend fun addBodyMeasurement(bodyMeasurement: BodyMeasurement): Task<Void>{
        val task =TailorDataBase.Usercollection
            .document(TailorDataBase.firebaseAuth.currentUser!!.uid)
            .collection("Body Measurement").document().set(bodyMeasurement)
        return task
    }

    override suspend fun getBodyMeasurement(userId: String): Task<DocumentSnapshot> {
        val task = TailorDataBase.Usercollection.document(userId).collection("Body Measurement")
            .document().get()
        return task

    }
    override suspend fun deleteOrder(docId: String):Task<Void> {

        val task =TailorDataBase.Usercollection
            .document(TailorDataBase.firebaseAuth.currentUser!!.uid).collection("Orders")
            .document(docId)
            .delete()
        return task

    }

    override suspend fun updateBodyMeasurement(bodyMeasurement: Map<String, Double>) {
        TailorDataBase.userSize.update(bodyMeasurement)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }

    override suspend fun updateUserProfile(
        userModel: Map<String, Any>,
        userId: String
    ): Task<Void>{
        var task =TailorDataBase.Usercollection.document(userId).set(userModel, SetOptions.merge())

        return task
    }

    override suspend fun uploadImage(imgFile: File) : UploadTask{
        var img = Uri.fromFile(imgFile)
        var storageRef = TailorDataBase.fireStorage.child("images/userIDTime.jpg")
       val task =  storageRef.putFile(img)
        return task
    }

    companion object{
        private var instance: DatabaseRepository? = null

        fun init(context: Context){
            if(instance == null){
                instance = DatabaseRepository(context)
            }
        }

        fun get(): DatabaseRepository{
            return instance ?: throw Exception("Room repository must be initialized")
        }
    }
}
