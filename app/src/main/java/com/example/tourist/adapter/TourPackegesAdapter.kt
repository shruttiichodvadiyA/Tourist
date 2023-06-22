package com.example.tourist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tourist.R
import com.example.tourist.ModelClass.TourPackegesModelClass

class TourPackegesAdapter(var context: Context?, var tourPackegesModelClass: ArrayList<TourPackegesModelClass>, var onClick:((TourPackegesModelClass)->Unit) ):RecyclerView.Adapter<TourPackegesAdapter.adapter>(){
    class adapter(v:View):RecyclerView.ViewHolder(v) {
        var imgtour: ImageView = v.findViewById(R.id.imgtour)
        var txtTourCountryName: TextView = v.findViewById(R.id.txtTourCountryName)
        var txtCountryDetails: TextView = v.findViewById(R.id.txtCountryDetails)
        var lnrTourPackeges:LinearLayout=v.findViewById(R.id.lnrTourPackeges)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapter {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.tour_packeges_itemfile, parent, false)
        val adapter = adapter(v)
        return adapter
    }

    override fun onBindViewHolder(holder: adapter, position: Int) {
        holder.apply {
            holder.txtTourCountryName.setText(tourPackegesModelClass[position].name)
            holder.txtCountryDetails.setText(tourPackegesModelClass[position].details)
            context?.let { Glide.with(it).load(tourPackegesModelClass[position].image).placeholder(R.drawable.placeholderimg).into(holder.imgtour) }
            holder.lnrTourPackeges.setOnClickListener {
                onClick.invoke(tourPackegesModelClass[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return tourPackegesModelClass.size
    }
}