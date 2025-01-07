package com.umut.bilecikcitypromotion.Admin.View

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.umut.bilecikcitypromotion.R
import java.io.ByteArrayOutputStream

class TarihiYerDuzenleActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private var tarihiYerId: String? = null
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarihi_yer_duzenle)

        db = FirebaseFirestore.getInstance()
        tarihiYerId = intent.getStringExtra("tarihiYerId")

        val editAdi = findViewById<EditText>(R.id.editAdi)
        val editKisaAciklama = findViewById<EditText>(R.id.editKisaAciklama)
        val editUzunAciklama = findViewById<EditText>(R.id.editUzunAciklama)
        val editImageView = findViewById<ImageView>(R.id.editImageView)
        val selectImageButton = findViewById<Button>(R.id.selectImageButton)

        // Firestore'dan mevcut veriyi çek
        tarihiYerId?.let { id ->
            db.collection("TarihiYerler").document(id).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        editAdi.setText(document.getString("t_yer_adi"))
                        editKisaAciklama.setText(document.getString("k_aciklama"))
                        editUzunAciklama.setText(document.getString("u_aciklama"))

                        val base64Image = document.getString("base64Image")
                        if (!base64Image.isNullOrEmpty()) {
                            val decodedBytes = Base64.decode(base64Image, Base64.DEFAULT)
                            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                            editImageView.setImageBitmap(bitmap)
                        }
                    }
                }
        }

        // Fotoğraf seçme işlemi
        selectImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 100)
        }

        // Kaydet butonu
        findViewById<Button>(R.id.saveButton).setOnClickListener {
            val yeniAdi = editAdi.text.toString()
            val yeniKisaAciklama = editKisaAciklama.text.toString()
            val yeniUzunAciklama = editUzunAciklama.text.toString()

            val updatedData = mutableMapOf<String, Any>(
                "t_yer_adi" to yeniAdi,
                "k_aciklama" to yeniKisaAciklama,
                "u_aciklama" to yeniUzunAciklama
            )

            // Yeni fotoğrafı base64 olarak kaydet
            if (selectedImageUri != null) {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImageUri)
                val outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                val base64Image = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
                updatedData["base64Image"] = base64Image
            }

            // Firestore'da belgeyi güncelle
            tarihiYerId?.let { id ->
                db.collection("TarihiYerler").document(id)
                    .update(updatedData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Tarihi Yer başarıyla güncellendi", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this, "Güncelleme başarısız: ${exception.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.data
            findViewById<ImageView>(R.id.editImageView).setImageURI(selectedImageUri)
        }
    }
}