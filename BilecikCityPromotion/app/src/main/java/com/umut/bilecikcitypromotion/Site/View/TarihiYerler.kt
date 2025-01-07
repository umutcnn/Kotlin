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
import com.umut.bilecikcitypromotion.Admin.Adapter.TarihiYerAdapter
import com.umut.bilecikcitypromotion.Admin.Adapter.TarihiYerlerAdapter
import com.umut.bilecikcitypromotion.Admin.Model.TarihiYerler
import com.umut.bilecikcitypromotion.R
import com.umut.bilecikcitypromotion.databinding.FragmentTarihiYerlerBinding
class TarihiYerler : Fragment() {

    private var _binding: FragmentTarihiYerlerBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: TarihiYerlerAdapter
    private val postList: ArrayList<TarihiYerler> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTarihiYerlerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()

        firestoreVerileriAl()

        adapter = TarihiYerlerAdapter(postList) { selectedYer ->
            val intent = Intent(requireContext(), TarihiYerDetayActivity::class.java)
            intent.putExtra("id", selectedYer.id) // Sadece ID gönderiliyor
            startActivity(intent)
        }

        binding.recylerTerihiYerlerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recylerTerihiYerlerView.adapter = adapter
    }

    private fun firestoreVerileriAl() {
        db.collection("TarihiYerler")
            .orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Log.e("FirestoreError", "Hata oluştu: ${exception.localizedMessage}", exception)
                    Toast.makeText(requireContext(), "Bir hata oluştu. Lütfen tekrar deneyin.", Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    postList.clear()
                    snapshot.documents.forEach { document ->
                        val post = TarihiYerler(
                            id = document.id,
                            email = document.getString("userEmail") ?: "Bilinmeyen Kullanıcı",
                            adi = document.getString("t_yer_adi") ?: "Ad bilgisi yok",
                            k_aciklama = document.getString("k_aciklama") ?: "Kısa açıklama yok",
                            u_aciklama = document.getString("u_aciklama") ?: "Uzun açıklama yok",
                            base64Image = document.getString("base64Image") ?: ""
                        )
                        postList.add(post)
                    }
                    adapter.notifyDataSetChanged()
                } else {
                    if (postList.isNotEmpty()) {
                        postList.clear()
                        adapter.notifyDataSetChanged()
                    }
                    Toast.makeText(requireContext(), "Gösterilecek veri bulunamadı.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
