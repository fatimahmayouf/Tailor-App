package com.example.tailor.views.user

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tailor.model.user.Orders
import com.example.tailor.repositories.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "OrderViewModel"
class OrderViewModel:ViewModel() {

    val databaseService = DatabaseRepository.get()

    val orderLiveData = MutableLiveData<Orders>()
    val errorOrderLiveData = MutableLiveData<String>()



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
}