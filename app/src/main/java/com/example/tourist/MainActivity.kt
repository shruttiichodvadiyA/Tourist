package com.example.tourist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tourist.activity.LoginActivity
import com.example.tourist.activity.SignUpActivity
import com.example.tourist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initview()
    }

    private fun initview() {
        binding.btnlogin.setOnClickListener {
            var i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }
        binding.btnsignup.setOnClickListener {
            var i = Intent(this, SignUpActivity::class.java)
            startActivity(i)
        }

    }


}