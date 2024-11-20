package com.umut.benimilkuygulamam

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

        println("Merhaba kotlin")
        //yorum satırı
        println(5*2)
        println(10/5)
        println(5/2)

        //kotlinde veri tipleri ve değişkenler
        var x = 10
        println(x)
        println(x*20)

        x=30
        println(x*20)

        var y=5
        println(y)
        println(y+x)

        val z=20
        println(z*50)

        val ornek: Long =10
        println(ornek*10)

        val ornekShort : Short = 20
        val ornekByte : Byte = 15
        val ornekInt : Int = 30

        println(ornekByte*ornekShort)

        val kullaniciyasi = 35
        val kullaniciYasi = 35
        val kullanici_yasi = 35

        //double - float - kesirli sayilar
        println("-------Double&Float-------")

        val pi = 3.141529
        println(pi*2)

        println(5/2)

        val ornekDouble = 3.0

        val sonucDouble = pi*ornekDouble
        println(sonucDouble)

        val ornekFloat : Float = 2.25f
        println(ornekFloat * 2)

        //unsigned integer

        val unsignedByte : UByte =10u
        val unsignedShort : UShort =10u
        val unsignedInt : UInt =10u
        val unsignedLong : ULong =10u


        //string -> metinler
        println("-------String-------")

        val benimString = "Benim String'im"
        println(benimString)

        val ornekString : String = "ornek"
        val isim = "umut can"

        println(isim.uppercase())

        val soyisim = "yıldız"
        println(isim+" "+soyisim)

        val yas = "15"
        val ornekDeger = "20"
        println(yas + ornekDeger)

        val benimStr : String
        //benimStr.uppercase()
        benimStr = "brnim stringim" //initalize, init, initialization

        //conversion
        val yasInt = yas.toInt()
        val xLong = x.toLong()
        println(xLong)
        
        println(yas.toInt()*20)

        //veri yapıları



        //diziler array

        print("array----")

        val benimDizim = arrayOf(benimString ,"umut","can","yıldız")

        println(benimDizim[0])
        println(benimDizim[1])
        println(benimDizim[2])
        println(benimDizim[3])

        println(benimDizim.first())


        print("arrayList----")

        val isimListesi = arrayListOf("umut,","can", "deneme")
        println(isimListesi[0])
        println(isimListesi[1])
        println(isimListesi[2])

        println(isimListesi.size) //kaç eleman vardır
        isimListesi.add("deneme2")
        println(isimListesi[3])
        isimListesi[3] = "deneme3"
        println(isimListesi[3])

        //isimListesi.removeAt(3)

        val numaraListesi = arrayListOf<Int>() //boş liste tanımlarken

        val digerOrnekListe = ArrayList<Int>()

        numaraListesi.add(10)
        numaraListesi.add(20)
        numaraListesi.add(30)

        digerOrnekListe.add(40)
        digerOrnekListe.add(50)
        digerOrnekListe.add(60)

        println(numaraListesi[1] * digerOrnekListe[2])

        val karisikListe = arrayListOf(10,2,14,"umut", true) //karışık liste oluşturuyorum

        //tipi belli olmayan karışık liste oluturuyorum
        val karisikBosListe = arrayListOf<Any>()
        karisikBosListe.add(10)
        karisikBosListe.add("umut")
        karisikBosListe.add(true)

        println(karisikBosListe.get(0))


        print("set----") //tekrarlı elemanlar olmamalı

        val ornekDizi= arrayOf(10,10,10,10,20,30,40)
        println(ornekDizi[0])
        println(ornekDizi[1])

        val ornekSet = setOf(10,10,10,10,20,30,40)

        println(ornekSet.size)

        //setlerde index mantığı yoktur

        ornekSet.forEach{
            println(it)
        }

        val bosSetOrnegi = HashSet<String>()

        bosSetOrnegi.add("umut")
        bosSetOrnegi.add("umut")
        bosSetOrnegi.add("umut")
        bosSetOrnegi.add("umut")
        bosSetOrnegi.add("Can")

        bosSetOrnegi.forEach{
            println(it)
        }


        val ornekTekilSet = ornekDizi.toHashSet()
        ornekTekilSet.forEach{
            println(it)
        }

        print("Mapler----")
        //Anahtar - Değer eşleşmesi

        val yemekDizisi = arrayListOf("elma","armut", "karpuz")
        val kaloriDizisi = arrayListOf(100,150, 200)

        println("${yemekDizisi[0]}'nın kalorisi:  ${kaloriDizisi[0]}")


        val yemekKaloriMapi  = hashMapOf<String, Int>()
        yemekKaloriMapi.put("elma" , 100)
        yemekKaloriMapi.put("armut" , 100)
        yemekKaloriMapi.put("karpuz" , 100)
        println(yemekKaloriMapi["elma"])

        println(yemekKaloriMapi.get("armut"))
        yemekKaloriMapi.put("elma" , 300)
        println(yemekKaloriMapi["elma"])

        val ornekHashMap = HashMap<String, String>()
        ornekHashMap.put("umut","yıldız")
        ornekHashMap.put("asd","dsa")



        print("if kontrolleri----") //eğer şöyleyse böyle olsun
        println(3>5)

        var sayi = 10
        sayi += 1
        println(sayi)
        sayi++
        println(sayi)
        sayi--
        println(sayi)

        //mod alma kalanını bulma
        println(10%4)
        val skor = 10
        if(skor<10){
            println("oyunu kaybettiniz")

        }else if(skor>=10 && skor <20){
            println("oyun idare eder bir skor aldınız")
        }else if(skor>=20 && skor<30){
            println("güzel bir skor aldınız")
        }else{
            println("çok güzel bir skor aldınız")

        }

        println("when---------------")


        val notRakam = 4
        var notString =""

        when(notRakam){
            0 ->notString = "Geçersiz not"
            1 ->notString = "zayıf"
            2 ->notString = "kötü"
            3 ->notString = "orta"
            4 ->notString = "iyi"
            5 ->notString = "pek iyi"
            else -> notString = "Böyle bir değerlendirme bulunmamaktadır"

        }

        println(notString)

        println("While Döngüsü---------------")
        var j=0
        while (j<10){
           println(j)
           j+=1
        }
        println("Döngü sonlandı")

        println("For Döngüsü---------------")
        val baskaDizi= arrayListOf(5,10,15,2025,30)
        println(baskaDizi[0] /5 * 3)
        println(baskaDizi[1] /5 * 3)

        println("Döngü Basladı")
        for(i in baskaDizi){
            println(i/5 * 3)
        }
        println("Döngü sonlandı")

        for (num in 0..9){
            println(num * 10)
        }

        val benimStrinDizim = ArrayList<String>()
        benimStrinDizim.add("umut")
        benimStrinDizim.add("can")
        benimStrinDizim.add("yıldız")

        for (i in benimStrinDizim){
            println(i.uppercase())
        }

        benimStrinDizim.forEach{
            println(it.uppercase())
        }







    }
}