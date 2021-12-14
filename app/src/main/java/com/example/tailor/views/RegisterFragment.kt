package com.example.tailor.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.tailor.R
import com.example.tailor.database.TailorDataBase
import com.example.tailor.databinding.FragmentRegisterBinding
import com.example.tailor.model.user.UserModel
import com.example.tailor.util.Validator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

private const val TAG = "RegisterFragment"
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val validator = Validator()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fullNameEditText = binding.fullNameRegEditText.text
        val emailEditText = binding.emailRegEditText.text
        val phoneNumberEditText = binding.phoneNumberRegEditText.text
        val passEditText = binding.passwordEditText.text


        binding.signUpRegBtn.setOnClickListener {
            Log.d(TAG,fullNameEditText.toString())
            Log.d(TAG,emailEditText.toString())
            Log.d(TAG,phoneNumberEditText.toString())
            Log.d(TAG,passEditText.toString())
           // getInfo(fullNameEditText,emailEditText,phoneNumberEditText,passEditText)

            if(fullNameEditText.isNotEmpty()&&emailEditText.isNotEmpty()
                && phoneNumberEditText.isNotEmpty()&&passEditText.isNotEmpty()){

                if(validator.fullNameIsValid(fullNameEditText.toString())){
                    if(validator.emailIsValid(emailEditText.toString())){
                        if(validator.passwordISValid(passEditText.toString())){

                            FirebaseAuth.getInstance()
                                .createUserWithEmailAndPassword(emailEditText.toString(),passEditText.toString())
                            val userInfo = UserModel(fullNameEditText.toString(),emailEditText.toString(),
                            passEditText.toString(),"",phoneNumberEditText.toString().toInt())
                            //===========================================
                            var userDocument =TailorDataBase.Usercollection.document("${FirebaseAuth.getInstance().currentUser!!.uid}")
                                userDocument.set(userInfo).addOnSuccessListener {
                                Toast.makeText(requireActivity(), "registration success", Toast.LENGTH_SHORT).show()
                            }
                            //===========================================

                        }else {

                            Toast.makeText(
                                requireContext(),
                                "password is not valid",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }else {
                        Toast.makeText(
                            requireContext(),
                            "please, Make sure your email is correct",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }else {
                    Toast.makeText(
                        requireContext(),
                        "please, Make sure your name is correct",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else {
                Toast.makeText(
                    requireContext(),
                    "please all fields must be filled ",
                    Toast.LENGTH_SHORT
                ).show()

                Log.d(TAG,fullNameEditText.toString())
                Log.d(TAG,emailEditText.toString())
                Log.d(TAG,phoneNumberEditText.toString())
                Log.d(TAG,passEditText.toString())
            }
        }

        binding.loginRegBtn.setOnClickListener {
           // findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}