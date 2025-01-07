package com.umut.bilecikcitypromotion.Admin.Adapter

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umut.bilecikcitypromotion.Admin.Model.Ilceler
import com.umut.bilecikcitypromotion.Admin.Model.Sehir
import com.umut.bilecikcitypromotion.databinding.RecylerRowBinding

class SehirAdapter(private val sehirList: ArrayList<Sehir>, private val onDelete: (String) -> Unit, private val onEdit: (String) -> Unit) : RecyclerView.Adapter<SehirAdapter.SehirHolder>() {
   class SehirHolder(val binding: RecylerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SehirAdapter.SehirHolder {
        val binding = RecylerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return SehirAdapter.SehirHolder(binding)

    }


    override fun getItemCount(): Int {
        return sehirList.size

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

    override fun onBindViewHolder(holder: SehirAdapter.SehirHolder, position: Int) {
        holder.binding.receyclerAdiText.text = sehirList.get(position).sehirAdi
        holder.binding.receyclerEmailText.text = sehirList.get(position).email
        holder.binding.receyclerTanitimText.text = sehirList.get(position).aciklama

        // Base64 string'ini Bitmap'e dönüştür
        val base64Image = sehirList[position].base64Image
        val bitmap = decodeBase64ToBitmap(base64Image)

        // Görseli ImageView'a yükle
        if (bitmap != null) {
            holder.binding.recyclerImageView.setImageBitmap(bitmap)
        } else {
            // Görsel hatalı veya yoksa placeholder kullan
            println("gorsel hatali")
        }
        /// Delete button tıklama olayını ayarla
        holder.binding.deleteButton.setOnClickListener {
            showDeleteConfirmationDialog(holder.binding.root.context, sehirList.get(position).id)
        }

        // Edit button tıklama olayı
        holder.binding.editButton.setOnClickListener {
            onEdit(sehirList.get(position).id) // Düzenlenecek belge ID'sini döndür
        }
    }
    private fun showDeleteConfirmationDialog(context: android.content.Context, id: String) {
        AlertDialog.Builder(context)
            .setTitle("Silme İşlemi")
            .setMessage("Bu veriyi silemezsiniz sadece düzenleyebilirsiniz")

            .create()
            .show()
    }



}