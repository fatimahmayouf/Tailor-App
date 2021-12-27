package com.example.tailor.views.orders

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.tailor.databinding.FragmentHomeOrderBinding
import com.example.tailor.views.HomeViewModel

class HomeOrderFragment : Fragment() {

    lateinit var binding: FragmentHomeOrderBinding
    val viewModel: HomeViewModel by activityViewModels()

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

        Glide.with(requireActivity()).load(viewModel.homeItemImg).into(binding.homeOrderImg)
        binding.homeOrderPriceTxt.text = "${viewModel.homeItemPrice} SAR"

        binding.homeOrderButton.setOnClickListener {
            // add order
        }
    }


}