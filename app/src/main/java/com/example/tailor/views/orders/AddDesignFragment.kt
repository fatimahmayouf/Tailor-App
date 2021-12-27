package com.example.tailor.views.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.tailor.R
import com.example.tailor.databinding.FragmentAddDesignBinding

import com.example.tailor.util.setStatusBarColor


class AddDesignFragment : Fragment() {

    private lateinit var binding: FragmentAddDesignBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddDesignBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val drawingBoardFragment = DrawingBoardFragment()
        val orderCameraFragment = OrderCameraFragment()

        requireActivity().setStatusBarColor(R.color.cardview_light_background)

        binding.uploadDesignBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.addDesign_layout,orderCameraFragment)
                .commit()
        }

        binding.drawDesignBtn.setOnClickListener {
           //it.findNavController().navigate(R.id.action_addDesignFragment_to_drawingBoardFragment)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.addDesign_layout,drawingBoardFragment)
                .commit()
        }
    }
}