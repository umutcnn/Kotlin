package com.umut.higher_orderfunctions

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

        println("------Lambda------")

        yazdigimiYazdir("umut")

        val yazdigimiYazdirLamda ={verilenString: String -> println(verilenString) }
        yazdigimiYazdirLamda("yazdir test")

        val carpmaIslemiLamda ={a: Int, b:Int -> a*b }
        val sonuc = carpmaIslemiLamda(3,4)
        println(sonuc)

        val carpmaIkıncıVersiyon : ( Int, Int) -> Int = {a,b->a*b}
        println(carpmaIkıncıVersiyon(5,3))

        print("-------high order functions------")

        //filter
        val benimListem = arrayListOf(1,3,5,7,9,11,13,15,17,19)

        /*
        val kucukSayilarListesi = benimListem.filter { num -> num < 10 }
        for (numara in kucukSayilarListesi) {
            println(numara)
        }
        */

        val kucuksayilarYeniListesi = benimListem.filter {it<10}

        //map
        val karesiAlinmisSayilar = benimListem.map { it*it }
        /*
        for (numara in karesiAlinmisSayilar) {
            println(numara)
        }
        */

        //map&filter
        val mapFilterBirArada = benimListem.filter { it < 10 }.map { it*it }
       mapFilterBirArada.forEach{ println(it) }

        //kendi yaptığımız bir sınıfta map ve filter uygulama

        val sanatci1 = Sanatci("umut",23,"gitar")
        val sanatci2 = Sanatci("zeynep",35,"piyano")
        val sanatci3 = Sanatci("atlas",23,"saksafon")
        val sanatcilar = arrayListOf<Sanatci>(sanatci1,sanatci2,sanatci3)
        val otuzdanBuyukSanatcilarinEnstrumanlari = sanatcilar.filter { it.yas >30 }.map { it.enstruman }
        otuzdanBuyukSanatcilarinEnstrumanlari.forEach{ println(it) }


        //scope Fonksiyonları
        print("-------scope Fonksiyonları------")
        val benimInteger :Int? = null
        benimInteger?.let{
            println(it)
        }

        val yeniInteger = benimInteger?.let { it + 1 }?:0
        println(yeniInteger)


        //also:  bir filtreleme yapıldıktan sonra üstüne bunu da yap demek için kullanılır
        sanatcilar.filter { it.yas>30 }.also { it.forEach{println(it.isim)} }





    }

    fun yazdigimiYazdir(string: String){
        println(string)
    }
}