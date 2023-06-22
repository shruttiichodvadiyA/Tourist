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
import com.example.tourist.ModelClass.ImagesModelClass
import com.example.tourist.R

class TrendingAdapter(var context: Context?, var imagesModelClass: ArrayList<ImagesModelClass>, var onClick:((ImagesModelClass)->Unit)) :
    RecyclerView.Adapter<TrendingAdapter.adapter>() {
    class adapter(v: View) : RecyclerView.ViewHolder(v) {

        var imgcountry: ImageView = v.findViewById(R.id.imgcountry)
        var txtcountryname: TextView = v.findViewById(R.id.txtcountryname)
        var lnrTrendingDestinations: LinearLayout = v.findViewById(R.id.lnrTrendingDestinations)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapter {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.trending_country_itemfile, parent, false)
        val adapter = adapter(v)
        return adapter
    }

    override fun onBindViewHolder(holder: adapter, position: Int) {
        holder.apply {
            holder.txtcountryname.setText(imagesModelClass[position].name)
            context?.let { Glide.with(it).load(imagesModelClass[position].image).placeholder(R.drawable.placeholderimg).into(holder.imgcountry) }
            holder.lnrTrendingDestinations.setOnClickListener {
                onClick.invoke(imagesModelClass[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return imagesModelClass.size
    }
}