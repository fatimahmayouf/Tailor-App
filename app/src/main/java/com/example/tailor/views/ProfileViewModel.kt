package com.example.tailor.views

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tailor.model.user.BodyMeasurement
import com.example.tailor.model.user.Orders
import com.example.tailor.model.user.UserModel
import com.example.tailor.repositories.DatabaseRepository
import java.lang.Exception

private const val TAG = "ProfileViewModel"
class ProfileViewModel:ViewModel() {
    
    val databaseService = DatabaseRepository.get()
    
    val profileInfoLiveData = MutableLiveData<UserModel>()
    val profileInfoLiveDataError = MutableLiveData<String>()
    
    val profileMeasurementLiveData = MutableLiveData<BodyMeasurement>()
    val profileMeasurementLiveDataError = MutableLiveData<String>()
    
    val profileOrdersLiveData = MutableLiveData<Orders>()
    val profileOrdersLiveDataError = MutableLiveData<String>()
    
    fun getProfileInfo(){
        try {
           
    }catch (e:Exception){
        

        }    
    }
    
    fun getBodyMeasurement(){
        try {
            
        }catch (e:Exception){
            
        }
        
    }
    
    fun getUserOrders(){
        try {
            
        }catch (e:Exception){
            
        }
    }
    
}