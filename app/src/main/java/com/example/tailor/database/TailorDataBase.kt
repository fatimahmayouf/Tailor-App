package com.example.tailor.database

import android.annotation.SuppressLint
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object TailorDataBase {

     @SuppressLint("StaticFieldLeak")

     var databaseFireStore = FirebaseFirestore.getInstance()
    var firebaseAuth = FirebaseAuth.getInstance()
    var currentUse = firebaseAuth.currentUser!!.uid

    val Usercollection = databaseFireStore.collection("User")// parent
    var user = Usercollection.document(firebaseAuth.currentUser!!.uid)
   // val userInfo = user.collection("profile").document("personal information")
    val userSize = user.collection("userId Size").document("measurement")
    val userOrders = user.collection("UserIdOrders")







}