package com.example.tailor.database

import com.example.tailor.model.user.UserModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

object TailorDataBase {
     val database = FirebaseFirestore.getInstance()
     val Usercollection = database.collection("User")

    var orders = Usercollection.document("Orders")
    var SizeMeasurement = Usercollection.document("SizeMeasurement")

}