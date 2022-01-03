package com.example.tailor.views.orders

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tailor.model.user.Orders
import com.example.tailor.repositories.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception

private const val TAG = "OrderViewModel"
class OrderViewModel:ViewModel() {

    val databaseService = DatabaseRepository.get()

    val orderLiveData = MutableLiveData<Orders>()
    val errorOrderLiveData = MutableLiveData<String>()
    val uplaodOrderLiveData = MutableLiveData<Orders>()
    val uploadErrorLiveData = MutableLiveData<String>()



    fun addOrder(orderModel: Orders){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                databaseService.addOrder(orderModel).addOnSuccessListener {
                    orderLiveData.postValue(orderModel)
                    Log.d(TAG,orderModel.toString())

                }.addOnFailureListener {
                    errorOrderLiveData.postValue(it.message.toString())
                    Log.d(TAG,it.message.toString())

                }
            }catch (e:Exception){
                errorOrderLiveData.postValue(e.message.toString())
                Log.d(TAG,e.message.toString())
            }


        }
    }

    fun uploadImage(imgFile: File,order: Orders){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                databaseService.uploadImage(imgFile).addOnSuccessListener {
                    uplaodOrderLiveData.postValue(order)
                    Log.d(TAG," on success ")
                }.addOnFailureListener{
                    uploadErrorLiveData.postValue(it.message)
                    Log.d(TAG,it.message.toString())
                }

            }catch (e:Exception){
                uploadErrorLiveData.postValue(e.message)
                Log.d(TAG,e.message.toString())
            }
        }

    }

    fun getLocalImage(){

    }
}