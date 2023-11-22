package com.sigma.flick.feature.myaccount.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sigma.flick.R
import com.sigma.flick.base.BaseListAdapter
import com.sigma.flick.databinding.ItemDetailedRecordsBinding
import com.sigma.flick.feature.myaccount.adapter.data.DetailedData

class DetailedRecordsListAdapter: BaseListAdapter<DetailedData, ItemDetailedRecordsBinding>(
    R.layout.item_detailed_records), BaseListAdapter.OnItemClickListener {
    override fun action(data: DetailedData, binding: ItemDetailedRecordsBinding) {
        binding.name.text = data.name
        binding.date.text = data.time
        binding.coin.text = data.changedMoney
        binding.currentMoney.text = data.currentMoney
        binding.profileIcon.setImageResource(data.profileIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(ItemDetailedRecordsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onClick(v: View, position: Int) {

    }

}