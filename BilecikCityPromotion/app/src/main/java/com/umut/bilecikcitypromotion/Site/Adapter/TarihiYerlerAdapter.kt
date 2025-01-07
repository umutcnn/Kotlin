package com.umut.bilecikcitypromotion.Admin.Adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umut.bilecikcitypromotion.Admin.Model.TarihiYerler
import com.umut.bilecikcitypromotion.databinding.RecylerTarihiYerlerRowBinding

class TarihiYerlerAdapter(
    private val tarihiYerList: ArrayList<TarihiYerler>,
    private val onItemClick: (TarihiYerler) -> Unit // Lambda fonksiyonu
) : RecyclerView.Adapter<TarihiYerlerAdapter.TarihiYerlerHolder>() {

    class TarihiYerlerHolder(val binding: RecylerTarihiYerlerRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarihiYerlerHolder {
        val binding = RecylerTarihiYerlerRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TarihiYerlerHolder(binding)
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

    override fun onBindViewHolder(holder: TarihiYerlerHolder, position: Int) {
        val currentItem = tarihiYerList[position]

        holder.binding.titleText.text = currentItem.adi
        holder.binding.descriptionText.text = currentItem.k_aciklama

        // Görseli yükle
        val bitmap = decodeBase64ToBitmap(currentItem.base64Image)
        if (bitmap != null) {
            holder.binding.tarihiYerlerImageView.setImageBitmap(bitmap)
        } else {
            println("Görsel hatalı veya bulunamadı")
        }

        // "Devamı için Tıklayınız" butonu için tıklama dinleyicisi
        holder.binding.moreButton.setOnClickListener {
            onItemClick(currentItem)
        }
    }
}