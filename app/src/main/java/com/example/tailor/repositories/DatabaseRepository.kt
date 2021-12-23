package com.example.tailor.repositories

import android.content.Context
import android.util.Log
import com.example.tailor.database.ITailorDatabase
import com.example.tailor.database.TailorDataBase
import com.example.tailor.model.user.BodyMeasurement
import com.example.tailor.model.user.Orders
import com.example.tailor.model.user.UserModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.SetOptions
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
    override suspend fun addOrder(orderModel: Orders) {
        TailorDataBase.userOrders.document().set(orderModel)

            .addOnSuccessListener { Log.d(TAG, "your order added successfully ") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    override suspend fun getOrders() {
        TailorDataBase.userOrders.get()

            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
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
    override suspend fun deleteOrder(orderModel: Orders, docId: String) {
        TailorDataBase.userOrders.document(docId).delete()
                //في التطبيق
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
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
        var a =TailorDataBase.Usercollection.document(userId).set(userModel, SetOptions.merge())
            //.update(userModel.keys.toString(),userModel.values)
        //.update(userModel)
        return a
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
