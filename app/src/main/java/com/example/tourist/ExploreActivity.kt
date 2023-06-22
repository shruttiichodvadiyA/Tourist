package com.example.tourist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tourist.databinding.ActivityExploreBinding

class ExploreActivity : AppCompatActivity() {
    lateinit var binding: ActivityExploreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExploreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initview()
    }

    private fun initview() {

    }
}