package com.umut.besinprojesi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umut.besinprojesi.model.Besin
import com.umut.besinprojesi.roomdb.BesinDatabase
import com.umut.besinprojesi.service.BesinAPIService
import com.umut.besinprojesi.util.OzelSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BesinListesiViewModel(application: Application) : AndroidViewModel(application) {

    val besinler = MutableLiveData<List<Besin>>()
    val besinHataMesaji = MutableLiveData<Boolean>()
    val besinYukleniyor = MutableLiveData<Boolean>()

    private val besinApiServis = BesinAPIService()
    private val ozelSharedPreferences = OzelSharedPreferences(getApplication())


    private val guncellemeZamani = 10 * 60 * 1000* 1000*1000L

    fun refleshData(){
        val kaydetilmeZamani = ozelSharedPreferences.zamaniAl()

        if(kaydetilmeZamani != null && kaydetilmeZamani != 0L && System.nanoTime() - kaydetilmeZamani< guncellemeZamani){
            //roomadan verileri alacagiz
            verileriRoomdanAl()
        }else{
            verileriInternettenAl()
        }
    }

    private fun verileriRoomdanAl(){
        besinYukleniyor.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val besinListesi = BesinDatabase(getApplication()).BesinDAO().getAllBesin()
            withContext(Dispatchers.Main){
                besinleriGoster(besinListesi)
                Toast.makeText(getApplication(),"Besinleri Room'dan Aldık", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun refreshDataFromInternet(){
        verileriInternettenAl()
    }

    private fun verileriInternettenAl(){
        besinYukleniyor.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val besinListesi = besinApiServis.getData()
            withContext(Dispatchers.Main){
                besinYukleniyor.value = false
                //rooma kaydedeceğiz
                roomaKaydet(besinListesi)
                Toast.makeText(getApplication(), "Besinleri internetten aldık", Toast.LENGTH_LONG).show()


            }
        }
    }


    private fun besinleriGoster(besinListesi: List<Besin>){
        besinler.value = besinListesi
        besinHataMesaji.value = false
        besinYukleniyor.value = false
    }
    private fun roomaKaydet(besinListesi: List<Besin>){

        viewModelScope.launch {
            val dao = BesinDatabase(getApplication()).BesinDAO()
            dao.deleteAll()
            val uuidListesi = dao.insertAll(*besinListesi.toTypedArray())
            var i = 0
            while (i<besinListesi.size){
                besinListesi[i].uuid = uuidListesi[i].toInt()
                i += 1
            }
            besinleriGoster(besinListesi)
        }
        ozelSharedPreferences.zamaniKaydet(System.nanoTime())
    }




}