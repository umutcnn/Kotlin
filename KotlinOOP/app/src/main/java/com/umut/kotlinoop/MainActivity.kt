package com.umut.kotlinoop

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //encapsulation: objelerin ve fonksiyonların ne zaman nerede ve nasıl kullanabileceğine bu şekilde karar verebiliriz
        //private : Özel, public : Halka açık, protected: korunmuş , internal : iç anlamına gelmektedir

        var umut = Sanatci("umut can", 23, "fülüt")
        println(umut.isim)
        umut.isim = "umut can yıldız"
        println(umut.isim)
        println(umut.sakiSoyle())

        umut.sacRengi="siyah"
        umut.turYazdir()

        umut.setSacRengiParolali("siyahımsi", "umis")

        val zeynep = Sanatci("zeynep",20,"Bateri")
        zeynep.sakiSoyle()

        //inheritance : kalıtım bir üst fonksiyonun özelliklerini alması

        val kahraman = Kahraman("supermen", "kosmak")
        kahraman.kos()

        val muhtesemKahraman = MuhtesemKahraman("batman", "zengin")
        muhtesemKahraman.kos()
        println(muhtesemKahraman.isim)

        //polmorphism
        // Statik polmorphism
        val islemler=Islemler()
        println(islemler.cikarma(10,2))
        println(islemler.cikarma(10,2,3))
        println(islemler.cikarma(10,2,3,2))

        // Dinamik polmorphism
        val kedi = Hayvan()
        val kopek = Kopek()
        val ornekDizi = arrayOf(kedi,kopek)
        ornekDizi.forEach {
            it.sesCikar()
        }

        //abstraction
        //abstract Class, Interface

        //NOT: soyut sınıflardan nesne oluşturulamaz ancak kalıtım alınır
        //val insan = Insan()









    }
}