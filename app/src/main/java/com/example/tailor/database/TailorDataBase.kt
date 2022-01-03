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


   /* File localFile = File.createTempFile("images", "jpg");
    riversRef.getFile(localFile)
    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
            // Successfully downloaded data to local file
            // ...
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception exception) {
            // Handle failed download
            // ...
        }
    });
*/




}