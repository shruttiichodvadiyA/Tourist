package com.example.tourist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.tourist.ModelClass.DestinationsmodelClass
import com.example.tourist.adapter.DestinationsAdapter
import com.example.tourist.databinding.ActivityTrendingDestinationsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class TrendingDestinationsActivity : AppCompatActivity() {

    lateinit var binding: ActivityTrendingDestinationsBinding
    lateinit var firebaseDatabase: DatabaseReference
    private lateinit var auth: FirebaseAuth
    lateinit var firebaseAuth: FirebaseAuth
    var destinationsList = ArrayList<DestinationsmodelClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrendingDestinationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        initview()
    }

    private fun initview() {
//        var destinationsmodelClass = DestinationsmodelClass()
//        firebaseDatabase = FirebaseDatabase.getInstance() firebaseDatabase = FirebaseDatabase.getInstance()
//        firebaseDatabase.reference.child("Trending Click")
//            .addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    var adapter = DestinationsAdapter(this@TrendingDestinationsActivity ,destinationsList)
//                    for (i in snapshot.children) {
//                        var data = i.getValue(destinationsmodelClass::class.java)
//                        data?.let { it1 -> destinationsList.add(it1) }
//
//                        var manager =
//                            LinearLayoutManager(this@TrendingDestinationsActivity, LinearLayoutManager.VERTICAL, false)
//                        binding.rcvDestinations.layoutManager = manager
//                        binding.rcvDestinations.adapter = adapter
//                    }
//                }
//                override fun onCancelled(error: DatabaseError) {
//                }
//            })

        firebaseDatabase = FirebaseDatabase.getInstance().getReference()
        var key = intent.getStringExtra("key").toString()
        firebaseDatabase.child("click").child(key)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var hotelname = snapshot.child("name").value.toString()
                    var hotelimage = snapshot.child("image").value.toString()

                    binding.txtHotelName.setText(hotelname)
                    Glide.with(this@TrendingDestinationsActivity).load(hotelimage).into(binding.imghotel)
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


    }
}