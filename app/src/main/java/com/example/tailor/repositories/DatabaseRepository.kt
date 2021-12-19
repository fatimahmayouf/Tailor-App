package com.example.tailor.repositories

import android.content.Context
import android.util.Log
import com.example.tailor.database.ITailorDatabase
import com.example.tailor.database.TailorDataBase
import com.example.tailor.model.user.BodyMeasurement
import com.example.tailor.model.user.Orders
import com.example.tailor.model.user.UserModel
import com.google.android.gms.tasks.Task
import java.lang.Exception

private const val TAG = "DatabaseRepository"
class DatabaseRepository(context: Context):ITailorDatabase{

    override suspend fun registerUser(
        userModel: UserModel,
        userId: String
    ): Task<Void> {
        val a = TailorDataBase.Usercollection.document().collection("info").document(TailorDataBase.firebaseAuth.currentUser!!.uid).set(userModel)
        return a
    }

    override suspend fun getUserProfile(userId: String){
         TailorDataBase.Usercollection.document(userId).get()
                 // في التطبيق
            .addOnSuccessListener { document ->
                if (document != null) {
                    var requiredData = document.data
                    Log.d(TAG, "DocumentSnapshot data: $requiredData")

                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }
    override suspend fun addOrder(orderModel: Orders) {
        TailorDataBase.userOrders.document().set(orderModel)
                // في التطبيق
            .addOnSuccessListener { Log.d(TAG, "your order added successfully ") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    override suspend fun getOrders() {
        TailorDataBase.userOrders.get()
            // هنا في التطبيق
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    override suspend fun addBodyMeasurement(bodyMeasurement: BodyMeasurement) {
        TailorDataBase.userSize.set(bodyMeasurement)
            .addOnSuccessListener { Log.d(TAG, "bodyMeasurement has been added") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    override suspend fun deleteOrder(orderModel: Orders, docId: String) {
        TailorDataBase.userOrders.document(docId).delete()
                //في التطبيق
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }

    override suspend fun updateBodyMeasurement(bodyMeasurement: Map<String, Double>) {
        TailorDataBase.userSize.update(bodyMeasurement)
                //في التطبيق
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }

    }

    override suspend fun updateUserProfile(
        email: String,
        userModel: Map<String, Any>,
        userId: String
    ) {
        TailorDataBase.firebaseAuth.currentUser!!.updateEmail(email)
        TailorDataBase.Usercollection.document(userId).update(userModel)
                // في التطبيق
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }

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
