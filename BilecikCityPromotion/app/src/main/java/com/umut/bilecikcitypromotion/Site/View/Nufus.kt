package com.umut.bilecikcitypromotion.Site.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.umut.bilecikcitypromotion.Admin.Model.Ilceler
import com.umut.bilecikcitypromotion.Admin.Model.TarihiYerler
import com.umut.bilecikcitypromotion.Site.Adapter.IlceDagilimAdapter
import com.umut.bilecikcitypromotion.Site.Adapter.IlceSliderAdapter
import com.umut.bilecikcitypromotion.Site.Adapter.SliderAdapter
import com.umut.bilecikcitypromotion.databinding.FragmentNufusBinding
import java.text.SimpleDateFormat
import java.util.Locale

class Nufus : Fragment() {

    private var _binding: FragmentNufusBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: IlceDagilimAdapter
    private val postList: ArrayList<Ilceler> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNufusBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()

        setupSlider()
        firestoreVerileriAl()

        adapter = IlceDagilimAdapter(postList)

        binding.recylerNufusDagilimiTablosu.layoutManager = LinearLayoutManager(requireContext())
        binding.recylerNufusDagilimiTablosu.adapter = adapter

    }

    private fun setupSlider() {
        // RecyclerView için layout manager tanımla
        binding.sliderRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Firebase'den verileri çek
        db.collection("Ilceler")
            .orderBy("date", Query.Direction.DESCENDING)
            .limit(4)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val ilceList = ArrayList<Ilceler>()
                    for (document in documents) {
                        val ilce = Ilceler(
                            id = document.id,
                            email = document.getString("userEmail") ?: "Bilinmeyen Kullanıcı",
                            IlceAdi = document.getString("ilce_adi") ?: "Ad bilgisi yok",
                            aciklama = document.getString("ilce_aciklama") ?: "Kısa açıklama yok",
                            nufus_2020 = document.getLong("nufus_2020")?.toInt() ?: 0,
                            nufus_2022 = document.getLong("nufus_2022")?.toInt() ?: 0,
                            nufus_2023 = document.getLong("nufus_2023")?.toInt() ?: 0,
                            base64Image = document.getString("base64Image") ?: "" // Base64 görsel
                        )
                        ilceList.add(ilce)
                    }

                    // Adapter'i tanımla ve RecyclerView'a bağla
                    val ilceSliderAdapter = IlceSliderAdapter(ilceList)
                    binding.sliderRecyclerView.adapter = ilceSliderAdapter
                } else {
                    Toast.makeText(requireContext(), "Gösterilecek veri bulunamadı.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Veri çekme hatası: ${e.localizedMessage}", e)
                Toast.makeText(requireContext(), "Bir hata oluştu. Lütfen tekrar deneyin.", Toast.LENGTH_LONG).show()
            }
    }








    private fun firestoreVerileriAl() {
        db.collection("Ilceler")
            .orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Log.e("FirestoreError", "Hata oluştu: ${exception.localizedMessage}", exception)
                    Toast.makeText(requireContext(), "Bir hata oluştu. Lütfen tekrar deneyin.", Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    // Gelen veriyi işle
                    postList.clear() // Listeyi temizle
                    snapshot.documents.forEach { document ->
                        val post = Ilceler(
                            id = document.id,
                            email = document.getString("userEmail") ?: "Bilinmeyen Kullanıcı",
                            IlceAdi = document.getString("ilce_adi") ?: "Ad bilgisi yok",
                            aciklama = document.getString("ilce_aciklama") ?: "Kısa açıklama yok",
                            nufus_2020 = document.getLong("nufus_2020") ?.toInt() ?: 0,
                            nufus_2022 = document.getLong("nufus_2022") ?.toInt() ?: 0,
                            nufus_2023 = document.getLong("nufus_2023") ?.toInt() ?: 0,

                            base64Image = document.getString("base64Image") ?: "" // Base64 görsel
                        )
                        postList.add(post)
                    }

                    adapter?.notifyDataSetChanged()
                } else {
                    // Veri yoksa
                    if (postList.isNotEmpty()) {
                        postList.clear()
                        adapter?.notifyDataSetChanged()
                        Toast.makeText(requireContext(), "Gösterilecek veri bulunamadı.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }



}