package com.example.tailor.views.orders

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.tailor.databinding.FragmentOrderCameraBinding
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import java.io.File

const val REQUEST_CODE = 100

class OrderCameraFragment : Fragment() {
    lateinit var binding: FragmentOrderCameraBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderCameraBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pickImage()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            val imgePath = Matisse.obtainPathResult(data)[0]//user can select only one image
            val imgFile = File(imgePath)

            //add image in view
            Glide.with(requireActivity()).load(imgFile).into(binding.cameraOrderImg)
        }
    }

    fun pickImage(){
        Matisse.from(this).choose(MimeType.ofImage(),false)
            .capture(true)
            .captureStrategy(CaptureStrategy(true,"com.example.tailor"))
            .forResult(REQUEST_CODE)
    }
}