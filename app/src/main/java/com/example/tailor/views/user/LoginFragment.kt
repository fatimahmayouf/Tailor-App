package com.example.tailor.views.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tailor.R
import com.example.tailor.databinding.FragmentLoginBinding
import com.example.tailor.util.setStatusBarColor
import com.google.firebase.auth.FirebaseAuth
import kotlin.concurrent.fixedRateTimer

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLoginBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().setStatusBarColor(R.color.cardview_light_background)

        val registerFragment = RegisterFragment()
        binding.signUpLogBtn.setOnClickListener {
            //binding.loginLayout.visibility=View.INVISIBLE

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.login_layout,registerFragment)
                .commit()

            //fragmentManager?.popBackStack()


        }
        val email = binding.emailLogEditText.text
        val password = binding.passowrdLogEditText.text
        binding.loginLogBtn.setOnClickListener {
            if(email.isNotBlank()&& password.isNotBlank()){
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email.toString(),password.toString())
                    .addOnCompleteListener {
                            task -> if (task.isSuccessful){
                        Toast.makeText(requireContext(), "Logged in successfully", Toast.LENGTH_SHORT).show()
                        requireActivity().supportFragmentManager.popBackStack()
                        //binding.loginLayout.visibility=View.GONE
                       /* requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.login_layout,ProfileFragment()).commit()*/
                    }else{
                        Toast.makeText(requireContext(),task.exception!!.message.toString() , Toast.LENGTH_SHORT).show()
                    }
                    }
            }else{
                Toast.makeText(requireContext(), "please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
        }
    }
