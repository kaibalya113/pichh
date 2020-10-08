package com.kaibalya.picshh

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kaibalya.picshh.model.User
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.item_post.*

private const val TAG = "CreateActivity"
private const val PICK_PHOTO_CODE = 1234
class CreateActivity : AppCompatActivity() {
    private var photoPath: Uri? = null
    private var signediInUser: User? =null
    private lateinit var firestoreDb: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        firestoreDb = FirebaseFirestore.getInstance()

        firestoreDb.collection("users").document(
            FirebaseAuth.getInstance()
            .currentUser?.uid as String).get()
            .addOnSuccessListener { userSnapshot ->
                signediInUser = userSnapshot.toObject(User::class.java)
                Log.i(TAG, signediInUser.toString())
            }
            .addOnFailureListener{exception ->
                Log.i(TAG, exception.message)
            }

        btnId.setOnClickListener {
            Log.i(TAG, "Photo is opening")
            val imagePicker = Intent(Intent.ACTION_GET_CONTENT)
            imagePicker.type = "image/*"
            if(imagePicker.resolveActivity(packageManager) != null){
                startActivityForResult(imagePicker, PICK_PHOTO_CODE)
            }
        }
        btnSubmit.setOnClickListener {
            handleSubmitButton()
        }
    }
    private fun handleSubmitButton(){
        // first check photopath is present or not
        if(photoPath == null){
            Toast.makeText(this, "No photo is selected", Toast.LENGTH_SHORT).show()
            return
        }
        // then check description is nt null
        if(textDesc1.text.isBlank()){
            Toast.makeText(this, "Description should not empty", Toast.LENGTH_SHORT).show()
            return
        }
        if(signediInUser == null){
            Toast.makeText(this, "user is null",Toast.LENGTH_SHORT).show()
            return
        }

        // upload photo to firebase storage



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_PHOTO_CODE){
         if(resultCode == Activity.RESULT_OK){
             photoPath = data?.data
             Log.i(TAG, "photoPath $photoPath")
             imageView3.setImageURI(photoPath)
         }else{
             Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
         }
        }
    }
}