package com.umut.bilecikcitypromotion.Admin.Adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umut.bilecikcitypromotion.Admin.Model.Ilceler
import com.umut.bilecikcitypromotion.databinding.RecylerRowBinding

class IlceAdapter (private val ilceList : ArrayList<Ilceler>, private val onDelete: (String) -> Unit, private val onEdit: (String) -> Unit): RecyclerView.Adapter<IlceAdapter.IlceHolder>() {
    class IlceHolder(val binding: RecylerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IlceAdapter.IlceHolder {
        val binding = RecylerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return IlceAdapter.IlceHolder(binding)

    }

    override fun getItemCount(): Int {
        return ilceList.size

    }

    fun decodeBase64ToBitmap(base64String: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            null
        }
    }

    override fun onBindViewHolder(holder: IlceAdapter.IlceHolder, position: Int) {
        holder.binding.receyclerAdiText.text = ilceList.get(position).IlceAdi
        holder.binding.receyclerEmailText.text = ilceList.get(position).email
        holder.binding.receyclerTanitimText.text = ilceList.get(position).aciklama

        // Base64 string'ini Bitmap'e dönüştür
        val base64Image = ilceList[position].base64Image
        val bitmap = decodeBase64ToBitmap(base64Image)

        // Görseli ImageView'a yükle
        if (bitmap != null) {
            holder.binding.recyclerImageView.setImageBitmap(bitmap)
        } else {
            // Görsel hatalı veya yoksa placeholder kullan
            println("gorsel hatali")
        }
        // Delete button tıklama olayını ayarla
        holder.binding.deleteButton.setOnClickListener {
            onDelete(ilceList.get(position).id) // İlgili belge ID'sini geri döndürüyoruz
        }
        // Edit button tıklama olayı
        holder.binding.editButton.setOnClickListener {
            onEdit(ilceList.get(position).id) // Düzenlenecek belge ID'sini döndür
        }
    }


}