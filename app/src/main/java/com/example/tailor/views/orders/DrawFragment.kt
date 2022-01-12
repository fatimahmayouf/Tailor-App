package com.example.tailor.views.orders

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.example.tailor.databinding.FragmentDrawBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.kyanogen.signatureview.SignatureView
import com.zhihu.matisse.Matisse
import yuku.ambilwarna.AmbilWarnaDialog
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "DrawFragment"
class DrawFragment : Fragment() {

    lateinit var binding: FragmentDrawBinding
    var defaultColor: Int = 0
    lateinit var sigView: SignatureView
    lateinit var currentPhotoPath: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDrawBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sigView = binding.signatureView

        getPermission()
        defaultColor = Color.BLACK

        binding.penSizeSeekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                sigView.penSize = p1.toFloat()
                p0?.max = 50
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        binding.colorsImgBtn.setOnClickListener {
            openColorePicker()
        }
        binding.eraserImgBtn.setOnClickListener {
            sigView.clearCanvas()
        }
        binding.shareImageButton.setOnClickListener {
            if(!sigView.isBitmapEmpty){
                saveImage()
                val intent = Intent()
                intent.action = Intent.ACTION_SEND

                val imgePath = Matisse.obtainPathResult(intent)[0]
                val imgFile = File(imgePath)
                //val photoUri: Uri = FileProvider.getUriForFile(requireContext(), "com.example.tailor", saveImage())
                //Log.d(TAG,photoUri.toString())
                //val uri = BitmapFactory.decodeFile(currentPhotoPath)
                //Log.d(TAG,uri.toString())
               // val uri = Uri.parse(currentPhotoPath)
                //intent.putExtra(Intent.EXTRA_STREAM,photoUri)
                intent.putExtra(Intent.EXTRA_STREAM,imgFile)

                intent.type = "image/*"
                startActivity(Intent.createChooser(intent, "share to ..."))
            }
        }
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
    fun openColorePicker(){
        val amb = AmbilWarnaDialog(requireContext(), defaultColor,object : AmbilWarnaDialog.OnAmbilWarnaListener{
            override fun onCancel(dialog: AmbilWarnaDialog?) {

            }

            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                defaultColor = color
                sigView.penColor = color
            }

        })
        amb.show()
    }

    fun saveImage(): File{

        //var file = File("${createImageFile()}")
        val bitmap = sigView.signatureBitmap
        var bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,0,bos)
        var bitmapData = bos.toByteArray()

        FileOutputStream(createImageFile()).apply {
            write(bitmapData)
            flush()
            close()
        }

        Toast.makeText(requireActivity(), "Painting saved", Toast.LENGTH_SHORT).show()
        return createImageFile()
        }


    private fun createImageFile(): File {

        val timestamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        //the up method give whole absolute path with its package name
        return File.createTempFile(
            "PNG_${timestamp}_",
            ".png",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath// full path from the base
        }
    }
}