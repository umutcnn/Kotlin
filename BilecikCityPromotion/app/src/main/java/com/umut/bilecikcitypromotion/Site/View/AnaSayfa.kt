package com.umut.bilecikcitypromotion.Site.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.umut.bilecikcitypromotion.Admin.Model.Sehir
import com.umut.bilecikcitypromotion.Admin.Model.TarihiYerler
import com.umut.bilecikcitypromotion.Site.Adapter.CityAdapter
import com.umut.bilecikcitypromotion.Site.Adapter.SliderAdapter
import com.umut.bilecikcitypromotion.databinding.FragmentAnaSayfaBinding
import java.text.SimpleDateFormat
import java.util.Locale

class AnaSayfa : Fragment() {

    private var _binding: FragmentAnaSayfaBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnaSayfaBinding.inflate(inflater, container, false)
        val view = binding.root

        setupSlider()
        setupCityList()

        return view
    }

    private fun setupSlider() {
        // RecyclerView için layout manager tanımla
        binding.sliderRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Firebase'den verileri çek
        db.collection("TarihiYerler")
            .orderBy("date", Query.Direction.DESCENDING)
            .limit(4)
            .get()
            .addOnSuccessListener { documents ->
                val tarihiYerList = ArrayList<TarihiYerler>()
                for (document in documents) {
                    val id = document.getString("id") ?: ""
                    val adi = document.getString("t_yer_adi") ?: ""
                    val k_aciklama = document.getString("k_aciklama") ?: ""
                    val u_aciklama = document.getString("u_aciklama") ?: ""
                    val base64Image = document.getString("base64Image") ?: "" // Eksik olursa boş değer atanır
                    val dateTimestamp = document.getTimestamp("date")

                    // Tarihi formatla
                    val formattedDate = if (dateTimestamp != null) {
                        val date = dateTimestamp.toDate()
                        val dateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault())

                        dateFormat.format(date)
                    } else {
                        "Bilinmiyor"
                    }

                    // K_aciklama'ya formatlanmış tarihi ekle
                    val aciklamaWithDate = "$k_aciklama\n\nTarih: $formattedDate"

                    // Tüm alanları sağla


                    val email = document.getString("email") ?: "" // Firebase'den çekilmesi gereken email
                    val tarihiYer = TarihiYerler(id, email, adi, aciklamaWithDate, u_aciklama, base64Image)
                    tarihiYerList.add(tarihiYer)
                }
                // Adapter'i tanımla ve RecyclerView'a bağla
                val sliderAdapter = SliderAdapter(tarihiYerList)
                binding.sliderRecyclerView.adapter = sliderAdapter
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }

    private fun setupCityList() {
        // RecyclerView için layout manager tanımla
        binding.cityRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        // Firebase'den şehir verilerini çek
        db.collection("Sehir")
            .get()
            .addOnSuccessListener { documents ->
                val cityList = ArrayList<Sehir>()
                for (document in documents) {
                    val id = document.getString("id") ?: ""
                    val email = document.getString("email") ?: ""
                    val sehirAdi = document.getString("sehirAdi") ?: ""
                    val aciklama = document.getString("aciklama") ?: ""
                    val base64Image = document.getString("base64Image") ?: ""

                    val city = Sehir(id, email, sehirAdi, aciklama, base64Image)
                    cityList.add(city)
                }
                // Adapter'i tanımla ve RecyclerView'a bağla
                val cityAdapter = CityAdapter(cityList)
                binding.cityRecyclerView.adapter = cityAdapter
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
