package com.example.tailor.views.user

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tailor.repositories.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "UpdateUserInfoViewModel"
class UpdateUserInfoViewModel:ViewModel() {

    val databaseService = DatabaseRepository.get()

    var profileUpdateInfoLiveData = MutableLiveData<Map<String,Any>>()
    val profileUpdateInfoLiveDataError = MutableLiveData<String>()


    fun updateUserInfo(userModel: Map<String, Any>,userId:String){

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = databaseService.updateUserProfile(userModel,userId)
                response.addOnSuccessListener {

                    Log.d(TAG, "DocumentSnapshot successfully updated!")
                    profileUpdateInfoLiveData.postValue(userModel)
                }
                    .addOnFailureListener {
                            e -> Log.w(TAG, "Error updating document", e)
                        profileUpdateInfoLiveDataError.postValue(e.message.toString())
                    }

            }catch (e:Exception){
                profileUpdateInfoLiveDataError.postValue(e.message.toString())
                Log.d(TAG,e.message.toString())
            }
        }
    }
}