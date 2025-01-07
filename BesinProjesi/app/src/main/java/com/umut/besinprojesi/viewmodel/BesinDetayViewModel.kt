package com.umut.besinprojesi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.umut.besinprojesi.model.Besin
import com.umut.besinprojesi.roomdb.BesinDatabase
import kotlinx.coroutines.launch
import java.util.UUID

class BesinDetayViewModel(application: Application): AndroidViewModel(application) {

    val besinLiveData = MutableLiveData<Besin>()

    fun roomVerisiniAl(uuid : Int){
        viewModelScope.launch {
            val dao = BesinDatabase(getApplication()).BesinDAO()
            val besin = dao.getBesin(uuid)
            besinLiveData.value = besin
        }
    }
}