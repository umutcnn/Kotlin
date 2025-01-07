package com.umut.bilecikcitypromotion.Admin.Adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umut.bilecikcitypromotion.Admin.Model.TarihiYerler
import com.umut.bilecikcitypromotion.databinding.RecylerRowBinding

class TarihiYerAdapter (private val tarihiYerList : ArrayList<TarihiYerler>, private val onDelete: (String) -> Unit, private val onEdit: (String) -> Unit): RecyclerView.Adapter<TarihiYerAdapter.TarihiYerHolder>() {
    class TarihiYerHolder(val binding: RecylerRowBinding): RecyclerView.ViewHolder(binding.root){

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarihiYerHolder {
        val binding = RecylerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TarihiYerHolder(binding)
    }

    override fun getItemCount(): Int {
        return tarihiYerList.size

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


    override fun onBindViewHolder(holder: TarihiYerHolder, position: Int) {
        holder.binding.receyclerEmailText.text = tarihiYerList.get(position).email
        holder.binding.receyclerAdiText.text = tarihiYerList.get(position).adi
        holder.binding.receyclerTanitimText.text = tarihiYerList.get(position).k_aciklama

        // Base64 string'ini Bitmap'e dönüştür
        val base64Image = tarihiYerList[position].base64Image
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
            onDelete(tarihiYerList.get(position).id) // İlgili belge ID'sini geri döndürüyoruz
        }
        // Edit button tıklama olayı
        holder.binding.editButton.setOnClickListener {
            onEdit(tarihiYerList.get(position).id) // Düzenlenecek belge ID'sini döndür
        }
    }

}
