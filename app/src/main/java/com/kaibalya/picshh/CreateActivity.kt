package com.kaibalya.picshh

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.item_post.*

private const val TAG = "CreateActivity"
private const val PICK_PHOTO_CODE = 1234
class CreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        btnId.setOnClickListener {
            Log.i(TAG, "Photo is opening")
            val imagePicker = Intent(Intent.ACTION_GET_CONTENT)
            imagePicker.type = "image/*"
            if(imagePicker.resolveActivity(packageManager) != null){
                startActivityForResult(imagePicker, PICK_PHOTO_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_PHOTO_CODE){
         if(resultCode == Activity.RESULT_OK){
            val photoPath = data?.data
             imageView3.setImageURI(photoPath)
         }else{
             Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
         }
        }
    }
}