package com.example.tailor.views

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tailor.model.home.HomePhotoModel
import com.example.tailor.repositories.ApiRepositoryServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "HomeViewModel"
class HomeViewModel: ViewModel() {

    val apiService = ApiRepositoryServices.get()
    val homePhotoLiveData = MutableLiveData<List<HomePhotoModel>>()
    val errorHomeLiveData = MutableLiveData<String>()


    fun getHomePhoto(){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = apiService.getHomePhoto()
                if(response.isSuccessful){
                    response.body()?.run {
                        homePhotoLiveData.postValue(this)
                        Log.d(TAG,response.body().toString())
                    }
                }else{
                    errorHomeLiveData.postValue(response.message())
                    Log.d(TAG,response.message().toString())
                }
            }
        }catch (e: Exception){
            Log.d(TAG,e.message.toString())
        }
    }
}