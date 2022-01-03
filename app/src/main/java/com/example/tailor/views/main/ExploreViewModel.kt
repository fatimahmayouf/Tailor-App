package com.example.tailor.views.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tailor.model.explore.ExplorePhotoModel
import com.example.tailor.repositories.ApiRepositoryServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


private const val TAG = "ExploreViewModel"
class ExploreViewModel: ViewModel() {

    val apiService = ApiRepositoryServices.get()
    val explorePhotoLiveData = MutableLiveData<ExplorePhotoModel>()
    val errorLiveData = MutableLiveData<String>()

    var exploreItemPrice: Double= 0.00
    var exploreItemImg: String = " "

    fun getPhoto(){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = apiService.getExplorePhoto()
                if(response.isSuccessful){
                    response.body()?.run {
                        explorePhotoLiveData.postValue(this)
                        Log.d(TAG,response.body().toString())
                    }
                }else{
                    errorLiveData.postValue(response.message())
                    Log.d(TAG,response.message().toString())
                }
            }
        }catch (e: Exception){
            Log.d(TAG,e.message.toString())
        }
    }
}