package com.umut.superkahramanuygulamasi

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.umut.superkahramanuygulamasi.databinding.ActivityMainBinding
import com.umut.superkahramanuygulamasi.databinding.ActivityTanitimAktivitesiBinding

class TanitimAktivitesi : AppCompatActivity() {


    private lateinit var binding: ActivityTanitimAktivitesiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTanitimAktivitesiBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val adaptordenGelenIntent = intent
        // adaptordenGelenIntent.getStringArrayExtra("secilenKahraman", SuperKahraman::class.java)
        val secilenKahraman = adaptordenGelenIntent.getSerializableExtra("secilenKahraman") as SuperKahraman

        binding.imageView.setImageResource(secilenKahraman.gorsel)
        binding.IsimextView.text = secilenKahraman.isim
        binding.MeslekextView.text = secilenKahraman.meslek

    }
}