package com.example.tailor.views.user

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.tailor.R
import com.example.tailor.databinding.FragmentProfileBinding
import com.example.tailor.model.user.Orders
import com.example.tailor.repositories.DatabaseRepository
import com.example.tailor.util.setStatusBarColor
import com.example.tailor.views.adapters.OrderListAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firestore.v1.StructuredQuery

private const val TAG = "ProfileFragment"
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by activityViewModels()
    var isExpandedSize = true
    var isExpandedOrders = true

   // val orderList = listOf<Orders>()
    lateinit var orderAdapter: OrderListAdapter

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

        /*recyclerView = binding.orderRecyclerView
        recyclerView.adapter = orderAdapter*/
        //viewModel.getProfileInfo()


        // ===========================check Authentication=============================

        when (FirebaseAuth.getInstance().currentUser) {
            null -> {
                var loginFragment = LoginFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.profile_userlayout,loginFragment).commit()
               /* var registerFragment = RegisterFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.profile_userlayout,registerFragment).commit()*/
            }
            else ->{
                viewModel.getProfileInfo()
                profileObservers()
                //viewModel.getBodyMeasurement()
               // bodySizeObservers()

                //=========================orders list=================================
                orderAdapter = OrderListAdapter(requireContext(),viewModel)
                binding.orderRecyclerView.adapter = orderAdapter
                viewModel.getUserOrders()
                createNotificationChannel()
                ordersObservers()
            }
        }

        //===========================Logout===================================

        binding.logoutTxt.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            requireActivity().supportFragmentManager.popBackStack()
        }

        //=========================minimize and maximize layout================
        binding.expandLayout.visibility = if(isExpandedSize)View.GONE else View.VISIBLE
        binding.expandedLayoutOrders.visibility = if(isExpandedOrders)View.GONE else View.VISIBLE

        binding.toggleButtonSize.setOnClickListener {
            isExpandedSize = !isExpandedSize
            binding.expandLayout.visibility = if(isExpandedSize)View.GONE else View.VISIBLE
        }
        binding.toggleButtonOrders.setOnClickListener {
            isExpandedOrders = !isExpandedOrders
            binding.expandedLayoutOrders.visibility = if(isExpandedOrders)View.GONE else View.VISIBLE
        }

        //======================Update user personal information==================
        binding.editImgBtn.setOnClickListener {
            val updateUserInfoFragment = UpdateUserInfo()
            updateUserInfoFragment.show(requireActivity().supportFragmentManager,"edit profile")
        }

        //======================Seekbars for body measurement======================
        //Bust
        binding.bustSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.bustEditText.setText(p1.toString() + " cm")
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
        //Waist
        binding.WaistSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.waistEditText.setText(p1.toString() + " cm")
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        //Hip
        binding.hipSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.hipEditText.setText(p1.toString() + " cm")
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
        //sleeve
        binding.sleeveSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.sleeveEditText.setText(p1.toString() + " cm")
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        //Length
        binding.lengthSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.lengthEdittText.setText(p1.toString() + " cm")
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {

            }
            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

    }
    fun profileObservers(){
        viewModel.profileInfoLiveData.observe(viewLifecycleOwner,{
           binding.fullNameTextView.text = it.getValue("fullName").toString()
            binding.emailTextView.text = it.getValue("email").toString()
            binding.addressTextView.text = it.getValue("location").toString()
            binding.phoneTextView.text = it.getValue("phoneNumber").toString()
            Log.d(TAG,viewModel.profileInfoLiveData.toString())

        })
        viewModel.getProfileInfo()

        viewModel.profileInfoLiveDataError.observe(viewLifecycleOwner,{
            Log.d(TAG, it)
        })
    }



    fun bodySizeObservers(){
        viewModel.profileMeasurementLiveData.observe(viewLifecycleOwner,{

            Log.d(TAG,viewModel.profileMeasurementLiveData.toString())
        })
        viewModel.profileMeasurementLiveDataError.observe(viewLifecycleOwner,{
            Log.d(TAG,it)
        })
    }

    fun ordersObservers(){
        viewModel.profileOrdersLiveData.observe(viewLifecycleOwner,{
            Log.d(TAG,it.toString())
            orderAdapter.submitList(it)

        })
        viewModel.getUserOrders()
        viewModel.profileOrdersLiveDataError.observe(viewLifecycleOwner,{
            Log.d(TAG,it)
            Toast.makeText(requireActivity(), "it", Toast.LENGTH_SHORT).show()
        })
    }
    fun deleteObservers(){
        viewModel.deleteOrderLiveData.observe(viewLifecycleOwner,{
            Log.d(TAG,it.toString())
            //orderAdapter.deleteItem(it.docId)
            orderAdapter.submitList(it)
        })
        }


    fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                orderAdapter.CHANNEL_ID,
                orderAdapter.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                lightColor = Color.GREEN
                enableLights(true)

            }

            val manager = requireActivity().getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}
