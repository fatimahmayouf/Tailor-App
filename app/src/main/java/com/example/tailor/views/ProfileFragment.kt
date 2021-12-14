package com.example.tailor.views


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tailor.database.TailorDataBase
import com.example.tailor.databinding.FragmentProfileBinding

private const val TAG = "ProfileFragment"
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

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

        var fullName= binding.fullNameTextView.text
        var email = binding.emailTextView.text
        var phone = binding.phoneTextView.text

       /*     // if user is not logged in
        var loginFragment = LoginFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.profile_userlayout,loginFragment).commit()
            //.addToBackStack("back to profile")
*/

        TailorDataBase.Usercollection.get().addOnSuccessListener {
            for( document in it){
                Log.d(TAG,document.id)
                Log.d(TAG,document.data.toString())
               fullName= document.data.getValue("fullName").toString()
                email = document.data.getValue("email").toString()
                phone = document.data.getValue("phoneNumber").toString()
                Log.d(TAG,fullName.toString())
            }
        }.addOnFailureListener{
            Log.d(TAG,it.toString())
        }
    }
}