package com.example.tailor.views.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.tailor.R
import com.example.tailor.database.TailorDataBase
import com.example.tailor.databinding.FragmentRegisterBinding
import com.example.tailor.model.user.UserModel
import com.example.tailor.repositories.ApiRepositoryServices
import com.example.tailor.repositories.DatabaseRepository
import com.example.tailor.util.Validator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val TAG = "RegisterFragment"
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    val dataBaseService = DatabaseRepository.get()
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
        DatabaseRepository.get()

        val fullNameEditText = binding.fullNameRegEditText.text
        val emailEditText = binding.emailRegEditText.text
        val phoneNumberEditText = binding.phoneNumberRegEditText.text
        val passEditText = binding.passwordEditText.text

        binding.signUpRegBtn.setOnClickListener {

            if(fullNameEditText.isNotEmpty()&&emailEditText.isNotEmpty()
                && phoneNumberEditText.isNotEmpty()&&passEditText.isNotEmpty()){

                if(validator.fullNameIsValid(fullNameEditText.toString())){
                    if(validator.emailIsValid(emailEditText.toString())){
                        if(validator.passwordISValid(passEditText.toString())){

                            val userInfo = UserModel(fullNameEditText.toString(),emailEditText.toString(),
                            passEditText.toString(),"",phoneNumberEditText.toString().toInt())

                            //===========================================
                            FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailEditText.toString(),passEditText.toString())
                                .addOnCompleteListener{
                                        task ->
                                    if(task.isSuccessful){
                                        //val firebaseUser: FirebaseUser = task.result!!.user!!
                                        Toast.makeText(requireContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show()
                                        dataBaseService.registerUser(userInfo,TailorDataBase.firebaseAuth.currentUser!!.uid)
                                            .addOnSuccessListener { Log.d(TAG, "registered successfully") }
                                             .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

                                    }
                                    else{
                                        Toast.makeText(requireContext(), task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                                    }
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
            }
        }
        val loginFragment = LoginFragment()
        binding.loginRegBtn.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction().replace(R.id.registerFragment_layout,loginFragment)
                .commit()
        }
    }
}