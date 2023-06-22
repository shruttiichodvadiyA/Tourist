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
import com.example.tourist.ModelClass.DestinationsmodelClass
import com.example.tourist.R

class DestinationsAdapter(var context: Context, var destinationlist: ArrayList<DestinationsmodelClass>
) :RecyclerView.Adapter<DestinationsAdapter.Adapter>() {
    class Adapter(v:View):RecyclerView.ViewHolder(v) {
        var imgtour: ImageView = v.findViewById(R.id.imgtour)
        var txtTourCountryName: TextView = v.findViewById(R.id.txtTourCountryName)
        var txtCountryDetails: TextView = v.findViewById(R.id.txtCountryDetails)
        var lnrTourPackeges: LinearLayout =v.findViewById(R.id.lnrTourPackeges)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.destinations_itemfile, parent, false)
        val adapter = Adapter(v)
        return adapter    }

    override fun onBindViewHolder(holder: Adapter, position: Int) {
        holder.apply {
            holder.txtTourCountryName.setText(destinationlist[position].name)
            holder.txtCountryDetails.setText(destinationlist[position].details)
          Glide.with(context).load(destinationlist[position].image).placeholder(R.drawable.placeholderimg).into(holder.imgtour)

        }
    }

    override fun getItemCount(): Int {
        return destinationlist.size
    }
}