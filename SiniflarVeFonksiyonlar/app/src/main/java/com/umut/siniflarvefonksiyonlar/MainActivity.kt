package com.umut.siniflarvefonksiyonlar

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {

    var sayac = 0
    lateinit var benimKahraman: SuperKahraman

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        println("oncreate calistirildi")
        benimKahraman = SuperKahraman("umut", 23, "Bilgisayar müh")

        birinciFonksiyon()
        birinciFonksiyon()
        birinciFonksiyon()
        ikinciFonksiyon()

        cikarma(10, 20)

         val topam = toplama(10,20)
        println("Toplama sonucu: ${topam}")


        val benimString = "umut"

        val supermen = SuperKahraman("umut", 25, "kodcu")

        val batman = SuperKahraman("Bruce Wayne", 35, "Patron")

        //nullability
        //stringten integer çevirme
        val kullaniciGirdisi = "10"
        val kullaniciGirdisiInteger = kullaniciGirdisi.toIntOrNull()
        if (kullaniciGirdisiInteger != null){
            println(kullaniciGirdisiInteger * 2)
        }
        val benimDouble : Double? = null

        val kullaniciGirdisiDouble  = kullaniciGirdisi.toIntOrNull()

        // !! anlamı yüzdeyüz eminim null gelmeyecek
        //kullaniciGirdisiDouble!!.div(2)

        // ? bir şey nul geliyorsa nul döndürebiliriz
        kullaniciGirdisiDouble?.div(2)

        if (kullaniciGirdisiInteger != null){
            println(kullaniciGirdisiInteger * 2)
        }

        //elvis operatoru
        println(kullaniciGirdisiDouble?.div(2) ?: 20)

        //bu fonksiyon kullaniciGirdisiDouble objesi null değilse çalıştırılır yoksa çalıştırılmaz
        kullaniciGirdisiDouble?.let {
            println(it * 2)
        }



    }

    fun testFonksiyonu(){
        println(benimKahraman.isim)



    }





    fun birinciFonksiyon(){
        sayac++
        println("birinci fonksiyon şu kadar çalıştırıldı: ${sayac}")

    }
    fun ikinciFonksiyon(){
        println("ikinci fonksiyon calistirildi.")
        println(benimKahraman.yas)
    }

    //girdi almak
    fun cikarma(a: Int, b:Int){
        println("çıkarma sonucu: ${ a - b }")
    }

    //çıktı verme -> dondürme return

    fun toplama(a:Int,b:Int) : Int{
        return (a+b)
    }
}