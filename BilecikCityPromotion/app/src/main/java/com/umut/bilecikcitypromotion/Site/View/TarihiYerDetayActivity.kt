package com.umut.bilecikcitypromotion.Site.View

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.umut.bilecikcitypromotion.R
import com.umut.bilecikcitypromotion.databinding.ActivityTarihiYerDetayBinding

class TarihiYerDetayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTarihiYerDetayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTarihiYerDetayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Gönderilen ID'yi al
        val yerId = intent.getStringExtra("id")
        if (yerId != null) {
            firestoreDetayVerisiniAl(yerId)
        }
    }

    private fun firestoreDetayVerisiniAl(yerId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("TarihiYerler").document(yerId).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    binding.detayTitle.text = document.getString("t_yer_adi")
                    binding.detayShortDescription.text = document.getString("k_aciklama")
                    binding.detayLongDescription.text = document.getString("u_aciklama")
                    val base64Image = document.getString("base64Image") ?: ""
                    val bitmap = decodeBase64ToBitmap(base64Image)
                    if (bitmap != null) {
                        binding.detayImageView.setImageBitmap(bitmap)
                    }
                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                Toast.makeText(this, "Veri alınırken hata oluştu.", Toast.LENGTH_SHORT).show()
            }
    }

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
