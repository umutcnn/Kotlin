package com.umut.besinprojesi.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.umut.besinprojesi.model.Besin

@Dao
interface BesinDAO {

    @Insert
    suspend fun insertAll(vararg besin: Besin) : List<Long>
    //eklediği besinlerin id'sini int olarak veriyor


    @Query("SELECT *  FROM besin")
    suspend fun getAllBesin() : List<Besin>

    @Query("select * from besin where uuid = :besinId")
    suspend fun getBesin(besinId : Int) : Besin



    @Query("DELETE FROM besin")
    suspend fun deleteAll()



}