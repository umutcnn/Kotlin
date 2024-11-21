package com.umut.calisanlarprojesi

class Calisanlar( val isim: String, private var maas: Int, val departman: String, val yas : Int) {


    fun maasGoster() : Int{
        this.maas = maas
        var goster = maas
        return (goster)
    }

}