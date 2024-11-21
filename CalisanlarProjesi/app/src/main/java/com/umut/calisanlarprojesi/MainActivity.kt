package com.umut.calisanlarprojesi

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

        /*
        1-) Calisanlar sinifi olustur
        2-) Calısanların isim, maas, departman, yaş bilgilerini tutacağız
        3-) örnek 10 tane çalıştıran oluşturun bir listede bu çalışanları toparla (MainActivity içerisinde yapabilirsin) (yazılım, finans, satış)
        4-) isim bilgileri sadece okunabilir olucak. calisan.isim ="dedassda" deklinde değişiklik yapılmayacak
        5-) maas bilgileri okunabilir de olmayacak, yazılabilir de olmayacak sadece maasGoster denilen bir fonksiyonla okunabilecek
            Bu fonksiyon içerisine aldığı objenin maasını gösterecek
        6-) MainActivity içerisinde -> yaşı 30 dan büyük olan ve departmanı yazılım olan kişilerin maaşlarını tutan bir liste yap(maasGoster fonksiyonuyla yazdır)
        7-) MainActivity içerisinde -> yaşı 25 ten küçük olan kişilerin sadece isimlerini tutan bir liste yap

         */

        val calisan1 = Calisanlar("umut ",50000, "yazılım", 23)
        val calisan2 = Calisanlar("ali ",500000, "yazılım", 33)
        val calisan3 = Calisanlar("ahmet ",250000, "finans", 13)
        val calisan4 = Calisanlar("hasan ",350000, "satış", 43)
        val calisan5 = Calisanlar("mehmet ",750000, "finans", 28)
        val calisan6 = Calisanlar("demir ",150000, "yazılım", 48)
        val calisan7 = Calisanlar("osman ",50000, "satış", 38)
        val calisan8 = Calisanlar("kadir ",450000, "finans", 33)
        val calisan9 = Calisanlar("eray ",150000, "yazılım", 23)
        val calisan10 = Calisanlar("tuğrul ",150000, "satış", 26)
        //calisan1.isim= "dsalkn"

        calisan10.maasGoster()

        val calisanlarListesi = arrayListOf<Calisanlar>(calisan1,calisan2,calisan3,calisan4,calisan5,calisan6,calisan7,calisan8,calisan9,calisan10)

        val yasiotuzdanbuyukOlanlarinDepartmaniYazilimOlan = calisanlarListesi.filter { it.yas>25 }.filter { it.departman == "yazılım" }.map { println(it.maasGoster())  }

        val yasiYirmibestenKucukOlanlarinIsimleri = calisanlarListesi.filter { it.yas<25 }.map { it.isim }
        println(yasiYirmibestenKucukOlanlarinIsimleri)



    }
}