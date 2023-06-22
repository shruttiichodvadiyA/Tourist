package com.example.tourist.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.tourist.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        initview()
    }

    private fun initview() {
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        if (sharedPreferences.getBoolean("isLogin", false) == true) {
            var i = Intent(this, DashBoardActivity::class.java)
            startActivity(i)
            finish()
        }
        binding.imgback.setOnClickListener {
            onBackPressed()
        }
        binding.txtlogin.setOnClickListener {
            var i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }

        binding.btnsignup.setOnClickListener {
            var email = binding.edtemail.text.toString()
            var password = binding.edtpassword.text.toString()
            var username = binding.edtusername.text.toString()
            var mobilenumber = binding.edtmobilenumber.text.toString()

            if (username.isEmpty()) {
                Toast.makeText(this, "please enter your username", Toast.LENGTH_SHORT).show()
            } else if (email.isEmpty()) {
                Toast.makeText(this, "please enter your email", Toast.LENGTH_SHORT).show()
            } else if (mobilenumber.isEmpty()) {
                Toast.makeText(this, "please enter your mobilenumber", Toast.LENGTH_SHORT).show()
            } else if (password.isEmpty()) {
                Toast.makeText(this, "please enter your password", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT)
                            .show()

                        val myEdit: SharedPreferences.Editor = sharedPreferences.edit()
                        myEdit.putBoolean("isLogin",true)
                        myEdit.commit()
                        var i = Intent(this, DashBoardActivity::class.java)
                        startActivity(i)
                    }

                }.addOnFailureListener {
                    Log.e("TAG", "initview: " + it.message)
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}