package com.example.tailor.views.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.tailor.databinding.FragmentUpdateUserInfoBinding
import com.example.tailor.repositories.DatabaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath


private const val TAG = "UpdateUserInfo"
class UpdateUserInfo : DialogFragment() {

    private lateinit var binding: FragmentUpdateUserInfoBinding
    private val getViewModel : ProfileViewModel by activityViewModels()
    private val updateViewModel: UpdateUserInfoViewModel by activityViewModels()
    private lateinit var fullName : String
    private lateinit var email : String
    private lateinit var location : String
    private lateinit var phone : String
    private lateinit var map: Map<String,Any>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateUserInfoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DatabaseRepository.get()
        getObservers()

        binding.saveChangeBtn.setOnClickListener {
            if(binding.fullNameEditText.text.isNotEmpty() && binding.emailEditText.text.isNotEmpty() && binding.locationEditText.text.isNotEmpty() && binding.phoneEditText.text.isNotEmpty()){
                map = hashMapOf("fullName" to binding.fullNameEditText.text.toString(),
                    "email" to binding.emailEditText.text.toString(),
                    "location" to binding.locationEditText.text.toString(),
                    "phoneNumber" to binding.phoneEditText.text.toString())

                updateViewModel.updateUserInfo(map,FirebaseAuth.getInstance().currentUser!!.uid)
                updateObservers()
                dismiss()


            }else{
                Toast.makeText(requireActivity(), "please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
        binding.cancelBtn.setOnClickListener {
            dismiss()
        }
    }

    fun getObservers(){
        getViewModel.profileInfoLiveData.observe(viewLifecycleOwner,{


            binding.fullNameEditText.setText(it.getValue("fullName").toString())
            binding.emailEditText.setText(it.getValue("email").toString())
            binding.phoneEditText.setText(it.getValue("phoneNumber").toString())
            binding.locationEditText.setText(it.getValue("location").toString())
        })
    }

    fun updateObservers(){
        updateViewModel.profileUpdateInfoLiveData.observe(viewLifecycleOwner,{

           fullName = it.getValue("fullName").toString()
           email = it.getValue("email").toString()
           location = it.getValue("location").toString()
           phone = it.getValue("phoneNumber").toString()
            getObservers()
            Toast.makeText(requireActivity(), "user information has been updated", Toast.LENGTH_SHORT).show()
        })

        updateViewModel.profileUpdateInfoLiveDataError.observe(viewLifecycleOwner,{
            Log.d(TAG,it)
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        })
    }
}