package com.umut.bilgisaklamaprojesi

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.umut.bilgisaklamaprojesi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //SharedPreferences
    lateinit var sharedPreferences : SharedPreferences
    private lateinit var binding: ActivityMainBinding
    var alinanKullaniciAdi : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        sharedPreferences = this.getSharedPreferences("com.umut.bilgisaklamaprojesi", Context.MODE_PRIVATE)
        alinanKullaniciAdi = sharedPreferences.getString("isim","")
        if(alinanKullaniciAdi == ""){
            binding.textView2.text = "Kaydedilen isim: "
        }
        else{
            binding.textView2.text = "Kaydedilen isim : ${alinanKullaniciAdi}"
        }
    }
    fun kaydet(view: View){
        val kullaniciIsmi= binding.editText.text.toString()
        if(kullaniciIsmi ==""){
            Toast.makeText(this@MainActivity,"isminizi boş bırakmamalısınız", Toast.LENGTH_LONG).show()
        }
        else{
            sharedPreferences.edit().putString("isim", kullaniciIsmi).apply()
            binding.textView2.text = "Kaydedilen isim : ${kullaniciIsmi}"
        }


    }

    fun sil(view: View){
        alinanKullaniciAdi = sharedPreferences.getString("isim","")
        if(alinanKullaniciAdi != ""){

            binding.textView2.text = "kaydedilen isim : "
            sharedPreferences.edit().remove("isim").apply()
        }

        binding.textView2.text = "Kaydedilen isim : "

    }
}