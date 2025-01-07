package com.umut.bilecikcitypromotion.Admin.View

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.umut.bilecikcitypromotion.R
import com.umut.bilecikcitypromotion.databinding.ActivityIlceYukleBinding

import java.io.IOException
import java.util.UUID

class IlceYukleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIlceYukleBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var selectedPicture: Uri? = null
    private var selectedBitmap: Bitmap? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIlceYukleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        registerLaunchers()
        binding.imageView.setOnClickListener { selectImage() }
        binding.yukleBtn.setOnClickListener { uploadImage() }
    }

    private fun uploadImage() {
        if (selectedPicture != null) {
            try {
                val uuid = UUID.randomUUID()
                val imageName = "$uuid.jpg"
                val inputStream = contentResolver.openInputStream(selectedPicture!!)
                val bytes = inputStream?.readBytes()
                val base64String = Base64.encodeToString(bytes, Base64.DEFAULT)

                val postMap = hashMapOf(
                    "imageName" to imageName,
                    "base64Image" to base64String,
                    "userEmail" to auth.currentUser?.email.toString(),
                    "ilce_adi" to binding.IlceAdiText.text.toString(),
                    "ilce_aciklama" to binding.IlceaciklamaText.text.toString(),
                    "nufus_2020" to binding.editTextNumber2020.text.toString().toIntOrNull(),
                    "nufus_2022" to binding.editTextNumber2022.text.toString().toIntOrNull(),
                    "nufus_2023" to binding.editTextNumber2023.text.toString().toIntOrNull(),
                    "date" to Timestamp.now()
                )

                db.collection("Ilceler").add(postMap)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Gönderi başarıyla yüklendi.", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Hata: ${it.localizedMessage}", Toast.LENGTH_LONG).show()
                    }
            } catch (e: Exception) {
                Toast.makeText(this, "Görsel dönüştürme hatası: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Lütfen bir görsel seçin!", Toast.LENGTH_LONG).show()
        }
    }

    private fun selectImage() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            android.Manifest.permission.READ_MEDIA_IMAGES
        } else {
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        }

        when {
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED -> {
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }
            shouldShowRequestPermissionRationale(permission) -> {
                Snackbar.make(binding.root, "Galeriye erişim için izin vermeniz gerekiyor.", Snackbar.LENGTH_INDEFINITE)
                    .setAction("İzin Ver") {
                        permissionLauncher.launch(permission)
                    }.show()
            }
            else -> {
                permissionLauncher.launch(permission)
            }
        }
    }

    private fun registerLaunchers() {
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intentFromResult = result.data
                if (intentFromResult != null) {
                    selectedPicture = intentFromResult.data
                    try {
                        selectedBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            val source = ImageDecoder.createSource(contentResolver, selectedPicture!!)
                            ImageDecoder.decodeBitmap(source)
                        } else {
                            MediaStore.Images.Media.getBitmap(contentResolver, selectedPicture)
                        }
                        binding.imageView.setImageBitmap(selectedBitmap)
                    } catch (e: IOException) {
                        Toast.makeText(this, "Görsel seçme hatası: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            } else {
                Snackbar.make(binding.root, "Galeri erişim izni reddedildi.", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}