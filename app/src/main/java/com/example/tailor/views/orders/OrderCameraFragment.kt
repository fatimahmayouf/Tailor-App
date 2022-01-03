package com.example.tailor.views.orders

import android.app.Activity.RESULT_OK
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.tailor.databinding.FragmentOrderCameraBinding
import com.example.tailor.model.user.Orders
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

const val REQUEST_CODE = 100

class OrderCameraFragment : Fragment() {
    lateinit var binding: FragmentOrderCameraBinding
    val orderViewModel: OrderViewModel by activityViewModels()
    lateinit var imgFile:File
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderCameraBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val addDesignFragment = AddDesignFragment()
        getPermission()
        pickImage()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            val imgePath = Matisse.obtainPathResult(data)[0]//user can select only one image
            imgFile = File(imgePath)

            //add image in view
            Glide.with(requireActivity()).load(imgFile).into(binding.cameraOrderImg)
        }

        binding.OrderCameraButton.setOnClickListener {
            orderViewModel.uploadImage(imgFile, Orders())//TODO
            //observers
        }
    }

    fun pickImage(){
        Matisse.from(this).choose(MimeType.ofImage(),false)
            .capture(true)
            .captureStrategy(CaptureStrategy(true,"com.example.tailor.views.orders"))
            .forResult(REQUEST_CODE)
    }

    fun getPermission() {
        Dexter.withContext(requireContext())
            .withPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {

                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Allow permission")
                        .setMessage("allow Tailor Application to access photos and camera on your device?")
                        .setNegativeButton("Cancel",
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                dialogInterface.dismiss()
                                p1?.cancelPermissionRequest()
                            }).setPositiveButton("Ok",
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                dialogInterface.dismiss()
                                p1?.continuePermissionRequest()
                            }).show()
                }
            }).check()
        Dexter.withContext(requireContext())
            .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {

                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Allow permission")
                        .setMessage("allow Tailor Application to access photos and files on your device?")
                        .setNegativeButton("Cancel",
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                dialogInterface.dismiss()
                                p1?.cancelPermissionRequest()
                            }).setPositiveButton("Ok",
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                dialogInterface.dismiss()
                                p1?.continuePermissionRequest()
                            }).show()
                }
            }).check()
    }

    fun uploadObservers(){
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        var currentDate = sdf.format(Date()).toString()

        orderViewModel.uplaodOrderLiveData.observe(viewLifecycleOwner,{
            //binding.cameraOrderImg = it.orderImg
            var note = binding.noteCameraEditText.text.toString()
            binding.cameraOrderPriceTxt.text = it.orderPrice.toString()
            note = it.orderNotes.toString()
            //currentDate = it.orderDate.toString()

        })
    }
}