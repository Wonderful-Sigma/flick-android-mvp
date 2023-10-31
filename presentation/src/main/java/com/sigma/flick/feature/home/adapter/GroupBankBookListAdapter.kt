package com.sigma.flick.feature.home.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import com.sigma.flick.R
import com.sigma.flick.base.BaseListAdapter
import com.sigma.flick.databinding.ItemGroupBankbookBinding
import com.sigma.flick.feature.home.adapter.data.ItemGroupBankBookData

class GroupBankBookListAdapter : BaseListAdapter<ItemGroupBankBookData, ItemGroupBankbookBinding>(
    R.layout.item_group_bankbook
), BaseListAdapter.OnItemClickListener {

    override fun action(data: ItemGroupBankBookData, binding: ItemGroupBankbookBinding) {
        binding.icon.setImageResource(data.icon)
        binding.coin.text = data.coin
        binding.bankbookName.text = data.bankbookName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(
            ItemGroupBankbookBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onClick(v: View, position: Int) {
        val bundle = Bundle()
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.animation_enter)
            .setExitAnim(R.anim.animation_exit)
            .setPopEnterAnim(R.anim.animation_pop_enter)
            .setPopExitAnim(R.anim.animation_pop_exit)
            .build()
        bundle.putInt("position", position)
        findNavController(v).navigate(R.id.collectionDetailFragment, bundle,navOptions)
    }

}