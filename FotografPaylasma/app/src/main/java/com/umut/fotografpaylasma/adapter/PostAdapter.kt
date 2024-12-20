package com.umut.fotografpaylasma.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.umut.fotografpaylasma.databinding.RecyclerRowBinding
import com.umut.fotografpaylasma.model.Post

class PostAdapter (private val postList : ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.PostHolder>() {

    class PostHolder(val binding: RecyclerRowBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size

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


    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.binding.receyclerEmailText.text = postList.get(position).email
        holder.binding.receyclerCommentText.text = postList.get(position).comment

        // Base64 string'ini Bitmap'e dönüştür
        val base64Image = postList[position].base64Image
        val bitmap = decodeBase64ToBitmap(base64Image)

        // Görseli ImageView'a yükle
        if (bitmap != null) {
            holder.binding.recyclerImageView.setImageBitmap(bitmap)
        } else {
            // Görsel hatalı veya yoksa placeholder kullan
            println("gorsel hatali")
        }
    }

}