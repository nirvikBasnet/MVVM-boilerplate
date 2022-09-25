package com.example.countries.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.countries.R
import com.example.countries.Utils.getProgressDrawable
import com.example.countries.Utils.loadImage
import com.example.countries.model.Country

class CountryListAdapter(var countries: ArrayList<Country>) :
    RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    fun updateCountries(newCountries : List<Country>){
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CountryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_country,parent,false)
    )

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    class CountryViewHolder(view: View): RecyclerView.ViewHolder(view){
        val countryName : TextView
        val flagImage : ImageView
        val capitalName : TextView

        private val progressDrawable = getProgressDrawable(view.context)


        init {
            countryName = view.findViewById(R.id.name)
            flagImage = view.findViewById(R.id.flagImage)
            capitalName = view.findViewById(R.id.capital)

        }

        fun bind(country: Country){
            countryName.text = country.countryName
            capitalName.text = country.capital
            flagImage.loadImage(country.flag, progressDrawable)
        }
    }


}