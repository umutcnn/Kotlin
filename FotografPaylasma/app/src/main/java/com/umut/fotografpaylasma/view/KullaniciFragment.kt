package com.umut.fotografpaylasma.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.umut.fotografpaylasma.databinding.FragmentKullaniciBinding


class KullaniciFragment : Fragment() {

    private var _binding: FragmentKullaniciBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentKullaniciBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.kayitBtn.setOnClickListener{ kayitOl(it) }
        binding.girisBtn.setOnClickListener{ girisYap(it) }

        val guncelKullanici = auth.currentUser
        if (guncelKullanici!= null){
            //kullanıcı daha önceden giriş yapmış
            val action = KullaniciFragmentDirections.actionKullaniciFragmentToFeedFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }

    fun kayitOl(view: View) {
        println("kayitOl fonksiyonu çağrıldı")

        val email = binding.emailText.text.toString().trim()
        val password = binding.passwordText.text.toString().trim()

        // E-posta ve şifre doğrulama
        if (email.isEmpty()) {
            showSnackbar(view, "Lütfen bir e-posta adresi girin")
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showSnackbar(view, "Geçerli bir e-posta adresi girin")
            return
        }

        if (password.isEmpty()) {
            showSnackbar(view, "Lütfen bir şifre girin")
            return
        }

        if (password.length < 6) {
            showSnackbar(view, "Şifre en az 6 karakter olmalı")
            return
        }

        // Firebase ile kayıt işlemi
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Kayıt başarılı
                    showSnackbar(view, "Kayıt başarılı bir şekilde yapıldı")
                    val action = KullaniciFragmentDirections.actionKullaniciFragmentToFeedFragment()
                    Navigation.findNavController(view).navigate(action)
                } else {
                    // Kayıt başarısız
                    showSnackbar(view, "Bir hata oluştu: ${task.exception?.localizedMessage}")
                }
            }
            .addOnFailureListener { exception ->
                // Hata mesajlarını kullanıcıya göster
                val errorMessage = when (exception) {
                    is FirebaseAuthWeakPasswordException -> "Şifre çok zayıf, lütfen daha güçlü bir şifre seçin"
                    is FirebaseAuthInvalidCredentialsException -> "Geçersiz bir e-posta adresi girdiniz"
                    is FirebaseAuthUserCollisionException -> "Bu e-posta adresiyle zaten bir hesap var"
                    else -> "Bir hata oluştu: ${exception.localizedMessage}"
                }
                showSnackbar(view, errorMessage)
            }
    }

    // Snackbar ile hata mesajını gösteren yardımcı fonksiyon
    private fun showSnackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }




    fun girisYap(view: View) {
        println("girisYap fonksiyonu çağrıldı")

        val userEmail = binding.emailText.text.toString().trim()
        val password = binding.passwordText.text.toString().trim()

        // Kullanıcı girişi doğrulama
        if (userEmail.isEmpty()) {
            showSnackbar(view, "Lütfen e-posta adresinizi girin")
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            showSnackbar(view, "Geçerli bir e-posta adresi girin")
            return
        }

        if (password.isEmpty()) {
            showSnackbar(view, "Lütfen şifrenizi girin")
            return
        }

        // Firebase ile giriş işlemi
        FirebaseAuth.getInstance().signInWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Giriş başarılı
                    val userEmail = FirebaseAuth.getInstance().currentUser?.email
                    showSnackbar(view, "Hoş geldin: $userEmail")
                    val action = KullaniciFragmentDirections.actionKullaniciFragmentToFeedFragment()
                    Navigation.findNavController(view).navigate(action)
                } else {
                    // Giriş başarısız
                    showSnackbar(view, "Giriş başarısız: ${task.exception?.localizedMessage}")
                }
            }
            .addOnFailureListener { exception ->
                // Hata mesajlarını kullanıcıya göster
                val errorMessage = when (exception) {
                    is FirebaseAuthInvalidCredentialsException -> "Geçersiz e-posta veya şifre"
                    is FirebaseAuthUserCollisionException -> "Bu e-posta adresi başka bir hesapta kullanılıyor"
                    else -> "Bir hata oluştu: ${exception.localizedMessage}"
                }
                showSnackbar(view, errorMessage)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}