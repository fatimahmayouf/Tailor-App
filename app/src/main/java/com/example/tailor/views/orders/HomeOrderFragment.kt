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
    var imageArray = arrayListOf<String>()

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

        observers()

        binding.homeOrderPriceTxt.text = "${homeViewModel.homeItemPrice} SAR"


        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        var currentDate = sdf.format(Date()).toString()

        imageArray.add( homeViewModel.imageArray[0])
        imageArray.add( homeViewModel.imageArray[1])
        imageArray.add( homeViewModel.imageArray[2])

        binding.carouselView.setImageListener { position, imageView ->
            Glide.with(requireActivity()).load(imageArray[position]).into(imageView)
        }
        binding.carouselView.pageCount = imageArray.size

        // clear array in viewModel
        homeViewModel.imageArray.removeAll(homeViewModel.imageArray)

        binding.homeOrderButton.setOnClickListener {
            val model = Orders(currentDate,homeViewModel.homeItemImg,homeViewModel.homeItemPrice, binding.homeNoteEditText.text.toString())

            orderViewModel.addOrder(model)
            Toast.makeText(requireContext(), "your order has been added", Toast.LENGTH_SHORT).show()

            it.findNavController().popBackStack()
        }
    }

    fun observers(){
        orderViewModel.orderLiveData.observe(viewLifecycleOwner,{
            homeViewModel.imageArray[0] = it.orderImg.toString()
            homeViewModel.imageArray[1] = it.orderImg2.toString()
            homeViewModel.imageArray[2] = it.orderImg3.toString()


            homeViewModel.homeItemPrice = it.orderPrice!!.toDouble()

        })
        orderViewModel.errorOrderLiveData.observe(viewLifecycleOwner,{
           Log.d(TAG,it)
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        })
    }
    }
