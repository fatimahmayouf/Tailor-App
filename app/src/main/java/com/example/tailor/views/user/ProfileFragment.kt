package com.example.tailor.views.user


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.tailor.R
import com.example.tailor.databinding.FragmentProfileBinding
import com.example.tailor.repositories.DatabaseRepository
import com.example.tailor.util.setStatusBarColor
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.GlobalScope

private const val TAG = "ProfileFragment"
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by activityViewModels()
    var isExpanded1 = true
    var isExpanded2 = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DatabaseRepository.get()
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setStatusBarColor(R.color.cardview_light_background)

        DatabaseRepository.get()
        viewModel.getProfileInfo()

        binding.expandLayout.visibility = if(isExpanded1)View.GONE else View.VISIBLE
        binding.expandedLayoutOrders.visibility = if(isExpanded1)View.GONE else View.VISIBLE


        when (FirebaseAuth.getInstance().currentUser) {
            null -> {
               /* var loginFragment = LoginFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.profile_userlayout,loginFragment).commit()*/
                var registerFragment = RegisterFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.profile_userlayout,registerFragment).commit()
            }
            else ->{
                viewModel.getProfileInfo()
                observers()

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

        binding.editImgBtn.setOnClickListener {
            val updateUserInfoFragment = UpdateUserInfo()
            updateUserInfoFragment.show(requireActivity().supportFragmentManager,"edit profile")
        }
    }


    fun observers(){
        viewModel.profileInfoLiveData.observe(viewLifecycleOwner,{
           binding.fullNameTextView.text = it.getValue("fullName").toString()
            binding.emailTextView.text = it.getValue("email").toString()
            binding.addressTextView.text = it.getValue("location").toString()
            binding.phoneTextView.text = it.getValue("phoneNumber").toString()
            Log.d(TAG,viewModel.profileInfoLiveData.toString())

        })

        viewModel.profileInfoLiveDataError.observe(viewLifecycleOwner,{
            Log.d(TAG, it)
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        })
    }
}