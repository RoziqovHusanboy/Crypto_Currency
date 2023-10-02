package com.example.cryptocurrency.utils

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cryptocurrency.data.dto.coinModel
import com.example.cryptocurrency.databinding.ItemCoinListBinding

class coinDiffUtils() : DiffUtil.ItemCallback<coinModel>() {
    override fun areItemsTheSame(oldItem: coinModel, newItem: coinModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: coinModel, newItem: coinModel): Boolean {
        return oldItem == newItem
    }
}

class CoinListAdapter :
    ListAdapter<coinModel, CoinListAdapter.coinListViewHolder>(coinDiffUtils()) {

    class coinListViewHolder(private val binding: ItemCoinListBinding) : ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(coinModel: coinModel) {
            binding.coinNameTv.text = "${coinModel.rank}. ${coinModel.name}"
            binding.statusTv.text = if (coinModel.is_active) "Active" else "Not active"
            binding.statusTv.setTextColor(if (coinModel.is_active) Color.GREEN else Color.RED )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): coinListViewHolder {
        return coinListViewHolder(
            ItemCoinListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: coinListViewHolder, position: Int) {
    holder.bind(getItem(position))
    }

}