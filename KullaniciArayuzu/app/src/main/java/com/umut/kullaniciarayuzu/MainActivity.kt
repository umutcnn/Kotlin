package com.umut.kullaniciarayuzu

import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.umut.kullaniciarayuzu.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
        binding.textView.text = "merhaba umut"
        /*binding.button.setOnClickListener{
            binding.textView.text = "butona tiklandi"
        }*/


        /* Binding kullanarak bunları çok daha kolay bir şekilde yazabiliriz
        val image = findViewById<ImageView>(R.id.imageView)
        val benimTextView = findViewById<TextView>(R.id.textView)
        benimTextView.text="Merhaba Kotlin"
        image.setImageResource(R.drawable.istanbul2)*/




    }
    fun kaydet(view : View){
        binding.textView.text = "kayıt edildi"
    }

    fun iptal(view : View){
        binding.textView.text = "iptal edildi"

    }

}