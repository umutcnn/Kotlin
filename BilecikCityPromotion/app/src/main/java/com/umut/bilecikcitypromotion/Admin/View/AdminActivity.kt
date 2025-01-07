package com.umut.bilecikcitypromotion.Admin.View

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.umut.bilecikcitypromotion.R
import com.umut.bilecikcitypromotion.Site.View.AnaSayfa
import com.umut.bilecikcitypromotion.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        replaceFragment(AdminAnaSayfa())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.Admin -> replaceFragment(AdminAnaSayfa())
                R.id.AdminTarihiYerler -> replaceFragment(AdminIlceler())
                R.id.adminTarihiYerler -> replaceFragment(AdminTarihiYerler())


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