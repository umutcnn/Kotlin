package com.umut.besinprojesi.roomdb

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.umut.besinprojesi.model.Besin

@Database (entities = [Besin::class], version = 1)
abstract class BesinDatabase : RoomDatabase() {
    abstract fun BesinDAO(): BesinDAO

    companion object{
        @Volatile
        private var instance : BesinDatabase? = null

        private val lock  =Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: databaseOlustur(context).also{
                instance = it
            }
        }

        private fun databaseOlustur(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            BesinDatabase::class.java,
            "BesinDatabase"
        ).build()





    }
}

