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
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.umut.bilecikcitypromotion.R
import java.io.ByteArrayOutputStream

class IlceDuzenleActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private var ilceId: String? = null
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ilce_duzenle)

        db = FirebaseFirestore.getInstance()
        ilceId = intent.getStringExtra("ilceId")

        val editIlceAdi = findViewById<EditText>(R.id.editIlceAdi)
        val editAciklama = findViewById<EditText>(R.id.editIlceAciklama)
        val editNufus2020 = findViewById<EditText>(R.id.editNufus2020)
        val editNufus2022 = findViewById<EditText>(R.id.editNufus2022)
        val editNufus2023 = findViewById<EditText>(R.id.editNufus2023)
        val editImageView = findViewById<ImageView>(R.id.editIlceImageView)
        val selectImageButton = findViewById<Button>(R.id.selectImageButton)

        // Firestore'dan mevcut veriyi çek
        ilceId?.let { id ->
            db.collection("Ilceler").document(id).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        editIlceAdi.setText(document.getString("ilce_adi"))
                        editAciklama.setText(document.getString("ilce_aciklama"))
                        editNufus2020.setText(document.getLong("nufus_2020")?.toString() ?: "")
                        editNufus2022.setText(document.getLong("nufus_2022")?.toString() ?: "")
                        editNufus2023.setText(document.getLong("nufus_2023")?.toString() ?: "")

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
            val yeniIlceAdi = editIlceAdi.text.toString()
            val yeniAciklama = editAciklama.text.toString()
            val yeniNufus2020 = editNufus2020.text.toString().toIntOrNull() ?: 0
            val yeniNufus2022 = editNufus2022.text.toString().toIntOrNull() ?: 0
            val yeniNufus2023 = editNufus2023.text.toString().toIntOrNull() ?: 0

            val updatedData = mutableMapOf<String, Any>(
                "ilce_adi" to yeniIlceAdi,
                "ilce_aciklama" to yeniAciklama,
                "nufus_2020" to yeniNufus2020,
                "nufus_2022" to yeniNufus2022,
                "nufus_2023" to yeniNufus2023
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
            ilceId?.let { id ->
                db.collection("Ilceler").document(id)
                    .update(updatedData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "İlçe başarıyla güncellendi", Toast.LENGTH_SHORT).show()
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
            findViewById<ImageView>(R.id.editIlceImageView).setImageURI(selectedImageUri)
        }
    }
}