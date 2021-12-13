package com.example.tailor.database

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object TailorDataBase {
     val database = FirebaseDatabase.getInstance()
     var tailorPref = database.getReference("User")
}