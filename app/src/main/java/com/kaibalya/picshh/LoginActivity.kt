package com.kaibalya.picshh

import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    val TAG = "MyMessage"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val auth = FirebaseAuth.getInstance()
        if(auth.currentUser != null){
            goToPostSection()
        }
        loginbtn.setOnClickListener {
            loginbtn.isEnabled = false
            val email = userEmailId.text.toString()
            val password =passwordId.text.toString()
            if(email.isBlank() || password.isBlank()){
                Toast.makeText(this, "Email and password should not empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Firebase authentication check

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                loginbtn.isEnabled = true
                if(task.isSuccessful){
                    Toast.makeText(this, "Sucessful!", Toast.LENGTH_SHORT).show()
                    // after this go to post page/ next page
                    goToPostSection()
                }else{
                    Log.i(TAG, "failed while logging in", task.exception)
                    Toast.makeText(this, "Failed while logging in!", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
    private fun goToPostSection(){
        Log.i(TAG, "nextpage")
        Toast.makeText(this, "next page", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, PostActivity::class.java)
        startActivity(intent)
        finish() // this means no longer part of back step
    }
}