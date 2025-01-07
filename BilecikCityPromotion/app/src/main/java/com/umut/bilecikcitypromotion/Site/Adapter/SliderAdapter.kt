package com.umut.bilecikcitypromotion.Site.Adapter

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umut.bilecikcitypromotion.databinding.SliderItemBinding
import com.umut.bilecikcitypromotion.Admin.Model.TarihiYerler
import com.umut.bilecikcitypromotion.Site.View.TarihiYerDetayActivity

class SliderAdapter(private val tarihiYerList: List<TarihiYerler>) :
    RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    class SliderViewHolder(val binding: SliderItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val binding = SliderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val tarihiYer = tarihiYerList[position]
        holder.binding.sliderTitle.text = tarihiYer.adi
        holder.binding.sliderDescription.text = tarihiYer.k_aciklama

        // Görseli yükleme
        val base64Image = tarihiYer.base64Image
        val bitmap = decodeBase64ToBitmap(base64Image)
        if (bitmap != null) {
            holder.binding.sliderImage.setImageBitmap(bitmap)
        }

        // Tıklama olayı
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, TarihiYerDetayActivity::class.java)
            intent.putExtra("id", tarihiYer.id) // ID'yi aktar
            Log.d("SliderAdapter", "Tıklanan ID: ${tarihiYer.id}")
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = tarihiYerList.size

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
