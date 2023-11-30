package com.wonderfulsigma.flick.feature.myaccount.adapter

import android.graphics.Color.parseColor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wonderfulsigma.flick.R
import com.wonderfulsigma.flick.base.BaseListAdapter
import com.wonderfulsigma.flick.databinding.ItemDetailedRecordsBinding
import com.wonderfulsigma.flick.feature.myaccount.adapter.data.DetailedData

class DetailedRecordsListAdapter: BaseListAdapter<DetailedData, ItemDetailedRecordsBinding>(
    R.layout.item_detailed_records), BaseListAdapter.OnItemClickListener {
    override fun action(data: DetailedData, binding: ItemDetailedRecordsBinding) {
        binding.name.text = data.name
        binding.date.text = data.time
        binding.coin.text = data.changedMoney
        binding.currentMoney.text = data.currentMoney
        if(data.changedMoney > "0")
            binding.coin.setTextColor(parseColor("#FF5B73FF"))
//        binding.profileIcon.setImageResource(data.profileIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(ItemDetailedRecordsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onClick(v: View, position: Int) {

    }

}