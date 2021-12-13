package com.example.tailor.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tailor.database.TailorDataBase
import com.example.tailor.databinding.FragmentRegisterBinding
import com.example.tailor.util.Validator
import com.google.firebase.database.FirebaseDatabase

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    //private val validator = Validator()
   // private lateinit var tailorDatabase: TailorDataBase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root

        //val tailorDatabase = FirebaseDatabase.getInstance()
        //var userPref = tailorDatabase.getReference("User")

        /*binding.signUpRegBtn.setOnClickListener {
            val emailEditText= binding.emailRegEditText.text
            val passEditText = binding.passwordEditText.text
            if(emailEditText.isNotBlank() && passEditText.isNotBlank()){
                if(validator.emailIsValid(emailEditText.toString())){
                    if(validator.passwordISValid(passEditText.toString())){
                        val email = emailEditText.toString()
                        val password = passEditText.toString()
                        tailorDatabase.tailorPref.push().setValue(email)
                        tailorDatabase.tailorPref.push().setValue(password)

                    }else{
                        Toast.makeText(requireContext(), "password is not valid", Toast.LENGTH_SHORT).show()

                    }


                }else{
                    Toast.makeText(requireContext(), "email is not valid", Toast.LENGTH_SHORT).show()

                }

            }else{
                Toast.makeText(requireContext(), "please all fields must be filled ", Toast.LENGTH_SHORT).show()
            }

        }*/

    }

}