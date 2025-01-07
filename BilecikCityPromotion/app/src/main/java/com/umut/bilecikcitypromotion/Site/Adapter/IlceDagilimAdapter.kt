package com.umut.bilecikcitypromotion.Site.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umut.bilecikcitypromotion.Admin.Model.Ilceler
import com.umut.bilecikcitypromotion.databinding.RecylerNufusDagilimTablosuBinding

class IlceDagilimAdapter(
    private val ilceList: ArrayList<Ilceler>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_ITEM = 1
    }

    class HeaderViewHolder(val binding: RecylerNufusDagilimTablosuBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ItemViewHolder(val binding: RecylerNufusDagilimTablosuBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RecylerNufusDagilimTablosuBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return if (viewType == VIEW_TYPE_HEADER) {
            HeaderViewHolder(binding)
        } else {
            ItemViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return ilceList.size + 1 // Başlık için +1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_HEADER) {
            val headerHolder = holder as HeaderViewHolder
            headerHolder.binding.tvIlce.text = "İlçe Adı"
            headerHolder.binding.tv2020.text = "2020 "
            headerHolder.binding.tv2022.text = "2022 "
            headerHolder.binding.tv2023.text = "2023 "
        } else {
            val itemHolder = holder as ItemViewHolder
            val ilce = ilceList[position - 1] // İlk öğe başlık olduğu için -1
            itemHolder.binding.tvIlce.text = ilce.IlceAdi
            itemHolder.binding.tv2020.text = ilce.nufus_2020.toString()
            itemHolder.binding.tv2022.text = ilce.nufus_2022.toString()
            itemHolder.binding.tv2023.text = ilce.nufus_2023.toString()


        }
    }


}