package com.sigma.flick.feature.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sigma.flick.R
import com.sigma.flick.base.BaseItemCallback
import com.sigma.flick.base.BaseListAdapter
import com.sigma.flick.databinding.ItemExplanationBinding
import com.sigma.flick.feature.home.adapter.data.ItemExplanationData

class ExplanationListAdapter : BaseListAdapter<ItemExplanationData, ItemExplanationBinding>(
    R.layout.item_explanation
), BaseListAdapter.OnItemClickListener {
    override fun action(data: ItemExplanationData, binding: ItemExplanationBinding) {
        binding.icon.setImageResource(data.icon)
        binding.detail.text = data.detail
        binding.title.text = data.title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(
            ItemExplanationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onClick(v: View, position: Int) {
        Log.d("클릭", position.toString())
    }

}
