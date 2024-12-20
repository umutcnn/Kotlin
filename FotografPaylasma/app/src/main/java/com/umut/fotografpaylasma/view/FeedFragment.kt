package com.umut.fotografpaylasma.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.umut.fotografpaylasma.R
import com.umut.fotografpaylasma.adapter.PostAdapter
import com.umut.fotografpaylasma.databinding.FragmentFeedBinding
import com.umut.fotografpaylasma.model.Post


class FeedFragment : Fragment(), PopupMenu.OnMenuItemClickListener{
    private var _binding: FragmentFeedBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var popup : PopupMenu
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    var adapter : PostAdapter? = null
    val postList : ArrayList<Post> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        db = Firebase.firestore

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatingActionButton.setOnClickListener{floatingButtonTiklandi(it)}
        popup = PopupMenu(requireContext(),binding.floatingActionButton)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.my_popup_menu,popup.menu)
        popup.setOnMenuItemClickListener(this)
        firestoreVerileriAl()

        adapter = PostAdapter(postList)
        binding.feedRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.feedRecyclerView.adapter = adapter




    }




    private fun firestoreVerileriAl() {
        db.collection("Posts")
            .orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    // Hata durumunda kullanıcıya mesaj göster
                    Toast.makeText(requireContext(), "Hata: ${exception.localizedMessage}", Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    postList.clear() // Listeyi her güncellemede temizle

                    val documents = snapshot.documents
                    for (document in documents) {
                        // Her alanın null kontrolünü yap
                        val userEmail = document.getString("userEmail") ?: "Bilinmeyen Kullanıcı"
                        val comment = document.getString("comment") ?: "Yorum Yok"
                        val base64Image = document.getString("base64Image") ?: ""  // Base64 görsel

                        // Post nesnesini oluştur
                        val post = Post(userEmail, comment, base64Image)
                        postList.add(post)
                    }

                    // Adapter'ı bilgilendir
                    adapter?.notifyDataSetChanged()
                } else {
                    // Eğer veri yoksa listeyi temizle ve adapteri bilgilendir
                    postList.clear()
                    adapter?.notifyDataSetChanged()
                    Toast.makeText(requireContext(), "Hiçbir gönderi bulunamadı.", Toast.LENGTH_SHORT).show()
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

        if (item?.itemId== R.id.yuklemeItem){
            val action = FeedFragmentDirections.actionFeedFragmentToYuklemeFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }else if (item?.itemId == R.id.cikisItem){
            //cikis yapma islemi
            auth.signOut()
            val action = FeedFragmentDirections.actionFeedFragmentToKullaniciFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }
        return true

    }

}