package com.umut.kotlinoop

class Sanatci (var isim: String, var yas: Int, var enstruman: String):Insan(), Dans, Sarki{

    var sacRengi = ""
    private var tur ="insan"

    var gozRengi = ""
        private set
        public get

    //kullanilmaz tamamen ornek icindir
    fun setSacRengiParolali(yeniSacRengi : String, parola : String){
        if (parola == "umis") {
            this.sacRengi = yeniSacRengi
        }else {
            println("parolan yanlis")
        }
    }
    fun turYazdir(){
        println(this.tur)
    }

    fun sakiSoyle(){
        println("Şu sanatci sarki soyledi: ${this.isim}")
    }

    init {//ornegin yeni bir sanatci olusturmadan önce yapmak istediğimiz bir sey varsa init fonksiyonunda yazabiliriz
        println("init cagirildi")
    }

    override fun sarkiSoyleFonksiyonu() {
        TODO("Not yet implemented")
    }

    override fun dansEtmeFonksiyonu() {
        TODO("Not yet implemented")
    }
}