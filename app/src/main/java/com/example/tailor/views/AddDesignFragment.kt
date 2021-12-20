package com.example.tailor.views

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tailor.R
import com.example.tailor.databinding.FragmentAddDesignBinding


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

        binding.drawDesignBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.addDesign_layout,drawingBoardFragment)
                .commit()
        }
    }
}