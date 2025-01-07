package com.umut.besinprojesi.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.umut.besinprojesi.databinding.FragmentBesinDetayBinding
import com.umut.besinprojesi.util.gorselIndir
import com.umut.besinprojesi.util.placeHolderYap
import com.umut.besinprojesi.viewmodel.BesinDetayViewModel


class BesinDetayFragment : Fragment() {

    private var _binding: FragmentBesinDetayBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel : BesinDetayViewModel
    var besinId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBesinDetayBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[BesinDetayViewModel::class.java]
        arguments?.let {
            besinId = BesinDetayFragmentArgs.fromBundle(it).uuid
        }

        viewModel.roomVerisiniAl(besinId)
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.besinLiveData.observe(viewLifecycleOwner){
            binding.besinIsim.text = it.besinIsim
            binding.besinkalori.text = it.besinKalori
            binding.besinkarbonhidrat.text = it.besinKarbonhidrat
            binding.besinprotein.text = it.besinProtein
            binding.besintag.text = it.besinYag
            binding.imageView2.gorselIndir(it.besinGorsel, placeHolderYap(requireContext()))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}