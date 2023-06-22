package com.example.tourist.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tourist.ModelClass.ImagesModelClass
import com.example.tourist.TrendingDestinationsActivity
import com.example.tourist.adapter.TrendingAdapter
import com.example.tourist.databinding.FragmentHomeBinding
import com.google.firebase.database.*

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var firebaseDatabase: DatabaseReference
    var imagelist = ArrayList<ImagesModelClass>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        initview()
        return binding.root
    }

    private fun initview() {
        var imagesModelClass = ImagesModelClass()
        firebaseDatabase = FirebaseDatabase.getInstance().getReference()
        firebaseDatabase.child("trending destinations")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var adapter = TrendingAdapter(context, imagelist) {

                        var i = Intent(context, TrendingDestinationsActivity::class.java)
                        startActivity(i)
                    }
                    for (i in snapshot.children) {
                        var data = i.getValue(imagesModelClass::class.java)
                        data?.let { it1 -> imagelist.add(it1) }

                        var manager =
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        binding.rcvTrendingDestinations.layoutManager = manager
                        binding.rcvTrendingDestinations.adapter = adapter

                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        binding.lnrexplore.setOnClickListener {
            var i = Intent(context, ExploreFragment::class.java)
            startActivity(i)
        }
    }


}