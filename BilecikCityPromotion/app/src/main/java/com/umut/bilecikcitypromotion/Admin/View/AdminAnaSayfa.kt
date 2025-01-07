package com.umut.bilecikcitypromotion.Admin.View

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.umut.bilecikcitypromotion.Admin.Adapter.SehirAdapter
import com.umut.bilecikcitypromotion.Admin.Model.Sehir
import com.umut.bilecikcitypromotion.R
import com.umut.bilecikcitypromotion.Site.View.MainActivity
import com.umut.bilecikcitypromotion.databinding.FragmentAdminAnaSayfaBinding


class AdminAnaSayfa : Fragment() , PopupMenu.OnMenuItemClickListener {
    private var _binding: FragmentAdminAnaSayfaBinding? = null
    private val binding get() = _binding!!
    private lateinit var popup : PopupMenu
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    var adapter : SehirAdapter? = null
    val postList : ArrayList<Sehir> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminAnaSayfaBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Firestore instance'ını başlat
        db = FirebaseFirestore.getInstance()

        // FloatingActionButton ile ilgili işlemler
        binding.floatingActionButton.setOnClickListener { floatingButtonTiklandi(it) }
        popup = PopupMenu(requireContext(), binding.floatingActionButton)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.my_popup_menu, popup.menu)
        popup.setOnMenuItemClickListener(this)



        // Firestore verilerini al
        firestoreVerileriAl()



        adapter = SehirAdapter(
            postList,
            onDelete = {
            },
            onEdit = { sehirId ->
                val intent = Intent(requireContext(), SehirDuzenleActivity::class.java)
                intent.putExtra("sehirId", sehirId) // Düzenlenecek şehir ID'sini gönder
                startActivity(intent)
            }
        )

        binding.feedRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.feedRecyclerView.adapter = adapter
    }

    private fun firestoreVerileriAl() {
        db.collection("Sehir")
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
                        val post = Sehir(
                            id = document.id, // Belge ID'sini alıyoruz
                            email = document.getString("userEmail") ?: "Bilinmeyen Kullanıcı",
                            sehirAdi = document.getString("sehirAdi") ?: "Ad bilgisi yok",
                            aciklama = document.getString("aciklama") ?: "Kısa açıklama yok",
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


    fun floatingButtonTiklandi(view: View){

        popup.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {

        if (item?.itemId== R.id.SiteAnaSayfa){
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }else if (item?.itemId== R.id.yuklemeItem){
            Toast.makeText(requireContext(), "Yeni bir şehir ekleme özelliği şu an bulunmamaktadır daha sonra tekrar deneyiniz!!!.", Toast.LENGTH_LONG).show()
        }else if (item?.itemId == R.id.cikisItem){
            //cikis yapma islemi
            auth.signOut()
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
        return true

    }

}


