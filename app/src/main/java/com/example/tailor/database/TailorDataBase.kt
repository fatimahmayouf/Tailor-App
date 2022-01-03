package com.example.tailor.database

import android.annotation.SuppressLint
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

object TailorDataBase {

     @SuppressLint("StaticFieldLeak")

     var databaseFireStore = FirebaseFirestore.getInstance()

    var firebaseAuth = FirebaseAuth.getInstance()

    val Usercollection = databaseFireStore.collection("User")// parent
    var user = Usercollection.document(firebaseAuth.currentUser!!.uid)
    var fireStorage = FirebaseStorage.getInstance().getReference("uploads")

    val userSize = user.collection("userId Size").document("measurement")


    /*Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
    StorageReference riversRef = storageRef.child("images/rivers.jpg");

    riversRef.putFile(file)
    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            // Get a URL to the uploaded content
            Uri downloadUrl = taskSnapshot.getDownloadUrl();
        }
    })
    .addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception exception) {
            // Handle unsuccessful uploads
            // ...
        }
    });*/





}