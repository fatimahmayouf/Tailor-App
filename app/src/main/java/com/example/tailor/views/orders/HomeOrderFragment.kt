package com.example.tailor.views.orders

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.tailor.databinding.FragmentHomeOrderBinding
import com.example.tailor.model.user.Orders
import com.example.tailor.views.main.HomeViewModel
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "HomeOrderFragment"
class HomeOrderFragment : Fragment() {

    lateinit var binding: FragmentHomeOrderBinding
    val homeViewModel: HomeViewModel by activityViewModels()
    val orderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeOrderBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(requireActivity()).load(homeViewModel.homeItemImg).into(binding.homeOrderImg)
        binding.homeOrderPriceTxt.text = "${homeViewModel.homeItemPrice} SAR"

        binding.homeNoteEditText

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        var currentDate = sdf.format(Date()).toString()

        val model = Orders(currentDate,homeViewModel.homeItemImg,homeViewModel.homeItemPrice, binding.homeNoteEditText.text.toString())
        binding.homeOrderButton.setOnClickListener {

            observers()
            orderViewModel.addOrder(model)
            Log.d(TAG,"after calling add")
            it.findNavController().popBackStack()
        }
    }

    fun observers(){


        orderViewModel.orderLiveData.observe(viewLifecycleOwner,{
            homeViewModel.homeItemImg = it.orderImg.toString()
            homeViewModel.homeItemPrice = it.orderPrice!!.toDouble()

            Toast.makeText(requireContext(), "your order has been added", Toast.LENGTH_SHORT).show()

        })
        orderViewModel.errorOrderLiveData.observe(viewLifecycleOwner,{
           Log.d(TAG,it)
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        })

    }

    }
