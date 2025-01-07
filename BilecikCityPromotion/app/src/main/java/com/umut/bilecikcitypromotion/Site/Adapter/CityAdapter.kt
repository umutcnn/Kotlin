package com.umut.bilecikcitypromotion.Site.Adapter

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umut.bilecikcitypromotion.Admin.Model.Sehir
import com.umut.bilecikcitypromotion.databinding.ItemCityBinding

class CityAdapter(private val cityList: List<Sehir>) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    inner class CityViewHolder(val binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = cityList[position]
        holder.binding.apply {
            cityNameTextView.text = city.sehirAdi
            cityDescriptionTextView.text = city.aciklama

            // Base64 görüntüyü çöz ve ImageView'e ayarla
            if (city.base64Image.isNotEmpty()) {
                val decodedBytes = Base64.decode(city.base64Image, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                cityImageView.setImageBitmap(bitmap)
            } else {
                cityImageView.visibility = View.GONE // Eğer görüntü yoksa ImageView'i gizle
            }
        }
    }

    override fun getItemCount(): Int = cityList.size
}
