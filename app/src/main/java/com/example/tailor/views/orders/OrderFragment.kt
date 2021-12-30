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
import com.example.tailor.R
import com.example.tailor.databinding.FragmentOrderBinding
import com.example.tailor.model.user.Orders
import com.example.tailor.util.setStatusBarColor
import com.example.tailor.views.ExploreViewModel
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "OrderFragment"
class OrderFragment : Fragment() {
    lateinit var binding: FragmentOrderBinding

    val viewModel : ExploreViewModel by activityViewModels()
    val orderViewModel: OrderViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrderBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setStatusBarColor(R.color.cardview_light_background)

        Glide.with(requireActivity()).load(viewModel.exploreItemImg).into(binding.exploreOrderImg)
        Log.d(TAG," this is image url: ${viewModel.exploreItemImg}")
        binding.exploreOrderPriceTxt.text = "${viewModel.exploreItemPrice} SAR"


        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        var currentDate = sdf.format(Date()).toString()

        val model = Orders(currentDate,viewModel.exploreItemImg,viewModel.exploreItemPrice, binding.noteEditText.text.toString())
        orderViewModel.addOrder(model)

        binding.OrderButton.setOnClickListener {
            observers()
            orderViewModel.addOrder(model)
            Log.d(TAG,"after calling add")
            it.findNavController().popBackStack()

        }
    }

    fun observers(){
        orderViewModel.orderLiveData.observe(viewLifecycleOwner,{
            viewModel.exploreItemImg = it.orderImg.toString()
            viewModel.exploreItemPrice = it.orderPrice!!.toDouble()

            Toast.makeText(requireContext(), "your order has been added", Toast.LENGTH_SHORT).show()

        })
        orderViewModel.errorOrderLiveData.observe(viewLifecycleOwner,{
            Log.d(TAG, it)
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        })

    }
}