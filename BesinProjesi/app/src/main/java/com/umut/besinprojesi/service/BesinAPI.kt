package com.umut.besinprojesi.service

import com.umut.besinprojesi.model.Besin
import retrofit2.http.GET

interface BesinAPI {

    //https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json


    //BASE URL -> https://raw.githubusercontent.com/
    //ENDPOINT -> atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    suspend fun getBesin() : List<Besin>

}