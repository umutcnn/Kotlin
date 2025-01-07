package com.umut.besinprojesi.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.umut.besinprojesi.adapter.BesinRecyclerAdapter
import com.umut.besinprojesi.databinding.FragmentBesinListeBinding
import com.umut.besinprojesi.service.BesinAPI
import com.umut.besinprojesi.viewmodel.BesinListesiViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create




class BesinListeFragment : Fragment() {

    private var _binding: FragmentBesinListeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BesinListesiViewModel
    private val besinRecyclerAdapter = BesinRecyclerAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBesinListeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[BesinListesiViewModel::class.java]
        viewModel.refleshData()


        binding.BesinRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.BesinRecyclerView.adapter = besinRecyclerAdapter


        binding.swipeRefleshLayout.setOnRefreshListener {
            binding.BesinRecyclerView.visibility = View.GONE
            binding.besinHataMesaji.visibility = View.GONE
            binding.BesinYukleniyor.visibility = View.GONE
            viewModel.refreshDataFromInternet()
            binding.swipeRefleshLayout.isRefreshing = false
        }

        observeLiveData()


    }

    private fun observeLiveData(){
        viewModel.besinler.observe(viewLifecycleOwner){
            //adapter
            besinRecyclerAdapter.besinListesiniGuncelle(it)
            binding.BesinRecyclerView.visibility = View.VISIBLE
        }
        viewModel.besinHataMesaji.observe(viewLifecycleOwner){
            if(it){
                binding.besinHataMesaji.visibility = View.VISIBLE
                binding.BesinRecyclerView.visibility = View.GONE
            }else{
                binding.besinHataMesaji.visibility= View.GONE
            }
        }

        viewModel.besinYukleniyor.observe(viewLifecycleOwner){
            if(it){
                binding.besinHataMesaji.visibility = View.GONE
                binding.BesinRecyclerView.visibility = View.GONE
                binding.BesinYukleniyor.visibility = View.GONE
            }else{
                binding.BesinYukleniyor.visibility = View.GONE
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}