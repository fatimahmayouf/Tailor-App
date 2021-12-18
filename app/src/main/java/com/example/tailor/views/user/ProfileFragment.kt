package com.example.tailor.views.user


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tailor.R
import com.example.tailor.database.TailorDataBase
import com.example.tailor.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

private const val TAG = "ProfileFragment"
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    var isExpanded1 = true
    var isExpanded2 = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.expandLayout.visibility = if(isExpanded1)View.GONE else View.VISIBLE
        binding.expandedLayoutOrders.visibility = if(isExpanded1)View.GONE else View.VISIBLE

        var fullName = binding.fullNameTextView
        var email = binding.emailTextView
        var phone = binding.phoneTextView

        when (FirebaseAuth.getInstance().currentUser) {
            null -> {
                /*var loginFragment = LoginFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.profile_userlayout,loginFragment).commit()*/
                var registerFragment = RegisterFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.profile_userlayout,registerFragment).commit()

            }
            else ->{

                // function
             /*   TailorDataBase.Usercollection.get().addOnSuccessListener {
                    for( document in it){
                        Log.d(TAG,document.id)
                        Log.d(TAG,document.data.toString())
                        fullName.text= document.data.getValue("fullName").toString()
                        email.text = document.data.getValue("email").toString()
                        phone.text= document.data.getValue("phoneNumber").toString()
                        Log.d(TAG,fullName.toString())
                    }
                }.addOnFailureListener{
                    Log.d(TAG,it.toString())
                }*/
            }
        }

        binding.logoutTxt.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.toggleButtonSize.setOnClickListener {
            isExpanded1 = !isExpanded1
            binding.expandLayout.visibility = if(isExpanded1)View.GONE else View.VISIBLE
        }
        binding.toggleButtonOrders.setOnClickListener {
            isExpanded2 = !isExpanded2
            binding.expandedLayoutOrders.visibility = if(isExpanded2)View.GONE else View.VISIBLE
        }
    }
}