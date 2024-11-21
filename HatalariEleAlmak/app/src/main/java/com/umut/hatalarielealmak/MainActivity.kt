package com.umut.hatalarielealmak

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {
    var globalX = 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sonuc = intOrnNullFonksiyon("umut")
        val sonuc2 = intOrnNullFonksiyon("10")
        println(sonuc)
        println(sonuc2)

        println(globalX)
        ornekFonksiyon()
        ornekFonksiyon2()


    }

    fun intOrnNullFonksiyon( str : String) : Int? {
        try {
            val numara = str.toInt()
            return numara
        }catch (e: Exception){
            e.printStackTrace()
        }


       return null
    }

    fun ornekFonksiyon(){
        globalX =20
        println(globalX)
    }
    fun ornekFonksiyon2(){
        globalX =30
        println(globalX)
    }
}