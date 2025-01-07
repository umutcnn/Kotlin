package com.umut.bilecikcitypromotion.Site.Adapter

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umut.bilecikcitypromotion.Admin.Model.Ilceler

import com.umut.bilecikcitypromotion.databinding.SliderItemBinding

class IlceSliderAdapter(private val ilceList: List<Ilceler>) :
    RecyclerView.Adapter<IlceSliderAdapter.IlceSliderViewHolder>() {

    class IlceSliderViewHolder(val binding: SliderItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IlceSliderViewHolder {
        val binding = SliderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IlceSliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IlceSliderViewHolder, position: Int) {
        val ilce = ilceList[position]
        holder.binding.sliderTitle.text = ilce.IlceAdi
        holder.binding.sliderDescription.text = ilce.aciklama

        // Görseli yükleme
        val base64Image = ilce.base64Image
        val bitmap = decodeBase64ToBitmap(base64Image)
        if (bitmap != null) {
            holder.binding.sliderImage.setImageBitmap(bitmap)
        }


    }

    override fun getItemCount(): Int = ilceList.size

    private fun decodeBase64ToBitmap(base64String: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            null
        }
    }
}