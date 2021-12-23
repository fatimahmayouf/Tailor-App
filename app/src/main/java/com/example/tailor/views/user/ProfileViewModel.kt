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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "ProfileViewModel"
class ProfileViewModel:ViewModel() {
    
    val databaseService = DatabaseRepository.get()
    
    var profileInfoLiveData = MutableLiveData<Map<String,Any>>()
    val profileInfoLiveDataError = MutableLiveData<String>()
    
    val profileMeasurementLiveData = MutableLiveData<Map<String,Any>>()
    val profileMeasurementLiveDataError = MutableLiveData<String>()
    
    val profileOrdersLiveData = MutableLiveData<Orders>()
    val profileOrdersLiveDataError = MutableLiveData<String>()
    
    fun getProfileInfo(){

            viewModelScope.launch(Dispatchers.IO) {
                try {
               val response = databaseService.getUserProfile(FirebaseAuth.getInstance().currentUser!!.uid)

                    response.addOnSuccessListener { document ->
                        if (document != null) {
                            var requiredData = document.data
                            Log.d(TAG, "DocumentSnapshot data: $requiredData")
                            profileInfoLiveData.postValue(requiredData!!)
                                Log.d(TAG,requiredData.toString())

                        }else{
                            Log.d(TAG, "No such document")
                        }
                    }.addOnFailureListener {
                            exception ->
                        Log.d(TAG, "get failed with ", exception)
                        profileInfoLiveDataError.postValue(exception.toString())
                    }
            }catch (e:Exception){
                    profileInfoLiveDataError.postValue(e.message.toString())
                    Log.d(TAG,e.message.toString())

                }

            }
    }
    
    fun getBodyMeasurement(){

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = databaseService.getBodyMeasurement(FirebaseAuth.getInstance().currentUser!!.uid)

                response.addOnSuccessListener { document ->
                    if (document != null) {
                        var requiredData = document.data
                        Log.d(TAG, "DocumentSnapshot data: $requiredData")
                        profileMeasurementLiveData.postValue(requiredData!!)
                        Log.d(TAG,requiredData.toString())

                    }else{
                        Log.d(TAG, "No such document")
                    }
                }.addOnFailureListener {
                        exception ->
                    Log.d(TAG, "get failed with ", exception)
                    profileMeasurementLiveDataError.postValue(exception.toString())
                }

            }catch (e:Exception){
                profileMeasurementLiveDataError.postValue(e.message.toString())
                Log.d(TAG,e.message.toString())

            }
        }

        
    }
    
    fun getUserOrders(){
        try {
            
        }catch (e:Exception){
            
        }
    }
    
}