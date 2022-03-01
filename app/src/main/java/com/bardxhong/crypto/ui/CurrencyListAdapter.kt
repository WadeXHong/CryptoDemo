package com.bardxhong.crypto.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bardxhong.crypto.databinding.ItemCurrencyInfoBinding
import com.bardxhong.crypto.domain.view_entities.CurrencyInfoViewEntity

class CurrencyListAdapter : RecyclerView.Adapter<CurrencyListAdapter.CurrencyInfoViewHolder>() {

    // TODO
    // 1. setter function for updating contents in list.
    // 2. use real data.
    // 2. use diffutils.
    private val dataList: MutableList<CurrencyInfoViewEntity> by lazy {
        mutableListOf(
            CurrencyInfoViewEntity("BTC", "Bitcoin", "BTC"),
            CurrencyInfoViewEntity("ETH", "Ethereum", "ETH"),
            CurrencyInfoViewEntity("XRP", "XRP", "XRP"),
        ).also { notifyDataSetChanged() }
    }

    fun updateList(list: List<CurrencyInfoViewEntity>) {
        dataList.clear()
        dataList.addAll(list.toList())
        // TODO use Diffutils to improve efficiency
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyInfoViewHolder {
        return CurrencyInfoViewHolder(
            ItemCurrencyInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = dataList.size


    override fun onBindViewHolder(holder: CurrencyInfoViewHolder, position: Int) {
        dataList.getOrNull(position)?.let { info ->
            holder.binding.tvCurrencyIcon.text = info.name.first().toString()
            holder.binding.tvCurrencyName.text = info.name
            holder.binding.tvCurrencySymbol.text = info.symbol
        }
    }

    inner class CurrencyInfoViewHolder(val binding: ItemCurrencyInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                Toast.makeText(
                    it.context,
                    "${dataList[layoutPosition]} clicked !",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}