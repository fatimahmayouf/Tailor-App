package com.example.tailor.views.user

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tailor.database.TailorDataBase
import com.example.tailor.model.user.BodyMeasurement
import com.example.tailor.model.user.Orders
import com.example.tailor.model.user.UserModel
import com.example.tailor.repositories.DatabaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.model.Document
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "ProfileViewModel"
class ProfileViewModel:ViewModel() {

    val databaseService = DatabaseRepository.get()

    var profileInfoLiveData = MutableLiveData<Map<String, Any>>()
    val profileInfoLiveDataError = MutableLiveData<String>()

    val profileMeasurementLiveData = MutableLiveData<Map<String, Any>>()
    val profileMeasurementLiveDataError = MutableLiveData<String>()

    val profileOrdersLiveData = MutableLiveData<List<Orders>>()
    val profileOrdersLiveDataError = MutableLiveData<String>()
    val deleteOrderLiveData = MutableLiveData<Orders>()
    val deleteOrderLiveDataError = MutableLiveData<String>()

    fun getProfileInfo() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response =
                    databaseService.getUserProfile(FirebaseAuth.getInstance().currentUser!!.uid)

                response.addOnSuccessListener { document ->
                    if (document != null) {
                        var requiredData = document.data
                        Log.d(TAG, "DocumentSnapshot data: $requiredData")
                        profileInfoLiveData.postValue(requiredData!!)
                        Log.d(TAG, requiredData.toString())

                    } else {
                        Log.d(TAG, "No such document")
                    }
                }.addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                    profileInfoLiveDataError.postValue(exception.toString())
                }
            } catch (e: Exception) {
                profileInfoLiveDataError.postValue(e.message.toString())
                Log.d(TAG, e.message.toString())

            }
        }
    }

    fun getBodyMeasurement() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response =
                    databaseService.getBodyMeasurement(FirebaseAuth.getInstance().currentUser!!.uid)

                response.addOnSuccessListener { document ->
                    if (document != null) {
                        var requiredData = document.data
                        Log.d(TAG, "DocumentSnapshot data: $requiredData")
                        profileMeasurementLiveData.postValue(requiredData!!)
                        Log.d(TAG, requiredData.toString())

                    } else {
                        Log.d(TAG, "No such document")
                    }
                }.addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                    profileMeasurementLiveDataError.postValue(exception.toString())
                }

            } catch (e: Exception) {
                profileMeasurementLiveDataError.postValue(e.message.toString())
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun getUserOrders() {
        val list = mutableListOf<Orders>()

        viewModelScope.launch (Dispatchers.IO){
            try {
                val response = databaseService.getOrders()
               response.addOnSuccessListener {
                   for (dc: DocumentChange in it.documentChanges){
                       if(dc.type == DocumentChange.Type.ADDED){
                           list.add(dc.document.toObject(Orders::class.java))
                       }
                   }
                   profileOrdersLiveData.postValue(list)
                   Log.d(TAG,"user Orders: $list")
               }
            }catch (e:Exception){
                profileOrdersLiveDataError.postValue(e.message)
            }
        }
    }

    fun deleteUserOrder(docId: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
               val response = databaseService.deleteOrder(docId)
                response.addOnSuccessListener {
                    deleteOrderLiveData.postValue(Orders())
                    //Log.d(TAG,docId)

                }.addOnFailureListener {
                    deleteOrderLiveDataError.postValue(it.message)
                    Log.d(TAG,it.message.toString())
                }
            }catch (e: Exception){
                deleteOrderLiveDataError.postValue(e.message)
                Log.d(TAG,e.message.toString())
            }
        }
    }
}
