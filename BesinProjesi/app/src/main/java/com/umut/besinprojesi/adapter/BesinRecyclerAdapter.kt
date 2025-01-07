package com.umut.besinprojesi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.umut.besinprojesi.databinding.BesinRecyclerRowBinding
import com.umut.besinprojesi.databinding.FragmentBesinListeBinding
import com.umut.besinprojesi.model.Besin
import com.umut.besinprojesi.util.gorselIndir
import com.umut.besinprojesi.util.placeHolderYap
import com.umut.besinprojesi.view.BesinListeFragmentDirections

class BesinRecyclerAdapter(val besinListesi : ArrayList<Besin>): RecyclerView.Adapter<BesinRecyclerAdapter.BesinViewHolder>() {
    class BesinViewHolder(val binding: BesinRecyclerRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
        val binding = BesinRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BesinViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return besinListesi.size
    }

    fun besinListesiniGuncelle(yeniBesinListesi : List<Besin>){
        besinListesi.clear()
        besinListesi.addAll(yeniBesinListesi)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {
        holder.binding.besiIsmi.text = besinListesi[position].besinIsim
        holder.binding.besinKalorisi.text = besinListesi[position].besinKalori

        holder.itemView.setOnClickListener{
            val action = BesinListeFragmentDirections.actionBesinListeFragmentToBesinDetayFragment(besinListesi[position].uuid)
            Navigation.findNavController(it).navigate(action)

        }
        holder.binding.imageView.gorselIndir(besinListesi[position].besinGorsel, placeHolderYap(holder.itemView.context))
    }


}