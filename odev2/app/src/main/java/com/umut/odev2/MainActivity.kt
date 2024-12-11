package com.umut.odev2

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.umut.odev2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bolme)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    fun toplama(view: View) {
        // Kullanıcıdan alınan girişleri string olarak al
        val sayi1Text = binding.sayi1.text.toString()
        val sayi2Text = binding.sayi2.text.toString()

        if(sayi1Text.isEmpty() or sayi2Text.isEmpty()){
            Toast.makeText(view.context, "Lütfen geçerli bir sayı girin!", Toast.LENGTH_SHORT).show()
        }
        else{
            val sayi1 = sayi1Text.toDouble()
            val sayi2 = sayi2Text.toDouble()
            val toplam = sayi1 + sayi2
            binding.textView.text = "sayilarin toplami ${toplam}"
        }
    }


    fun cikarma(view: View){
        // Kullanıcıdan alınan girişleri string olarak al
        val sayi1Text = binding.sayi1.text.toString()
        val sayi2Text = binding.sayi2.text.toString()
        if(sayi1Text.isEmpty() or sayi2Text.isEmpty()){
            Toast.makeText(view.context, "Lütfen geçerli bir sayı girin!", Toast.LENGTH_SHORT).show()
        }
        else{
            val sayi1 = sayi1Text.toDouble()
            val sayi2 = sayi2Text.toDouble()
            val toplam = sayi1 - sayi2
            binding.textView.text = "sayilarin toplami ${toplam}"
        }

    }fun carpma(view: View){
        // Kullanıcıdan alınan girişleri string olarak al
        val sayi1Text = binding.sayi1.text.toString()
        val sayi2Text = binding.sayi2.text.toString()
        if(sayi1Text.isEmpty() or sayi2Text.isEmpty()){
            Toast.makeText(view.context, "Lütfen geçerli bir sayı girin!", Toast.LENGTH_SHORT).show()
        }
        else{
            val sayi1 = sayi1Text.toDouble()
            val sayi2 = sayi2Text.toDouble()
            val toplam = sayi1 * sayi2
            binding.textView.text = "sayilarin toplami ${toplam}"
        }

    }fun bolme(view: View){
        // Kullanıcıdan alınan girişleri string olarak al
        val sayi1Text = binding.sayi1.text.toString()
        val sayi2Text = binding.sayi2.text.toString()
        if(sayi1Text.isEmpty() or sayi2Text.isEmpty()){
            Toast.makeText(view.context, "Lütfen geçerli bir sayı girin!", Toast.LENGTH_SHORT).show()
        }
        else{
            val sayi1 = sayi1Text.toDouble()
            val sayi2 = sayi2Text.toDouble()
            val toplam = sayi1 / sayi2
            binding.textView.text = "sayilarin toplami ${toplam}"
        }

    }

}
