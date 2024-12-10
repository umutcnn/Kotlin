package com.umut.androidalertdialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.umut.androidalertdialog.databinding.ActivityMainBinding

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

        //Context
        //aktivite context, app context

        // mesela uygulama açıldığında hostgeldiniz diye toast mesajı vermek istiyoruz bunu şu şekilde yapabiliriz
        Toast.makeText(this, "Hoşgeldiniz", Toast.LENGTH_LONG).show()

        /*
        binding.kaydet.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                println("butona Tıklandı")
                this@MainActivity //bu şekilde mainactivity gösterebiliriz
            }

        })*/



    }
    fun kaydet(view: View){
        val alert = AlertDialog.Builder(this@MainActivity)
        alert.setTitle("Kayıt et")
        alert.setMessage("Kayıt etmek istediğine emin misin?")

        alert.setPositiveButton("Evet", DialogInterface.OnClickListener { dialogInterface, i ->
            Toast.makeText(this@MainActivity, "Kayıt edildi", Toast.LENGTH_LONG).show()
        } )

        alert.setNegativeButton("Hayır", object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                Toast.makeText(this@MainActivity, "Kayıt edilmedi", Toast.LENGTH_LONG).show()

            }
        })
        alert.show()

    }
}