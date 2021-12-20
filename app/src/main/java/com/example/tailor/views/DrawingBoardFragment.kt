package com.example.tailor.views

import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tailor.R
import com.example.tailor.databinding.FragmentDrawingBoardBinding

private const val TAG = "DrawingBoardFragment"
class DrawingBoardFragment : Fragment() {
    
    private lateinit var binding: FragmentDrawingBoardBinding
    
    companion object{
        var path = Path()
        var paintBrush = Paint() 
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDrawingBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.brushImgBtn.setOnClickListener { 
            
        }
        binding.eraserImgBtn.setOnClickListener { 
            
        }
        binding.colorsImgBtn.setOnClickListener {  
            
        }
        
    }
}