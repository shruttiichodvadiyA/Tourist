package com.example.tourist.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tourist.ExploreActivity
import com.example.tourist.ModelClass.TourPackegesModelClass
import com.example.tourist.adapter.TourPackegesAdapter
import com.example.tourist.databinding.FragmentExploreBinding
import com.google.firebase.database.*

class ExploreFragment : Fragment() {
    lateinit var binding: FragmentExploreBinding
    lateinit var firebaseDatabase: DatabaseReference
    var tourlist = ArrayList<TourPackegesModelClass>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExploreBinding.inflate(layoutInflater)
        initview()
        return binding.root
    }

    private fun initview() {

        var tourPackegesModelClass = TourPackegesModelClass()
        firebaseDatabase = FirebaseDatabase.getInstance().getReference()
        firebaseDatabase.child("Tour Packeges")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var adapter = TourPackegesAdapter(context, tourlist) {
                            var i = Intent(context, ExploreActivity::class.java)
                            startActivity(i)
                    }
                    for (i in snapshot.children) {
                        var data = i.getValue(tourPackegesModelClass::class.java)
                        data?.let { it1 -> tourlist.add(it1) }

                        var manager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        binding.rcvTourPackeges.layoutManager = manager
                        binding.rcvTourPackeges.adapter = adapter
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

}