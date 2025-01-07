package com.umut.bilecikcitypromotion.Site.View

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.umut.bilecikcitypromotion.R
import com.umut.bilecikcitypromotion.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        replaceFragment(AnaSayfa())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.Home -> replaceFragment(AnaSayfa())
                R.id.Nufus -> replaceFragment(Nufus())
                R.id.TarihiYerler -> replaceFragment(TarihiYerler())
                R.id.Admin -> replaceFragment(GirisYap())

                else ->{

                }
            }
            true



        }

    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager =supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame,fragment)
        fragmentTransaction.commit()
    }
}