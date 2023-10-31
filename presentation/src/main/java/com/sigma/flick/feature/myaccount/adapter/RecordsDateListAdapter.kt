package com.sigma.flick.feature.myaccount.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sigma.flick.R
import com.sigma.flick.base.BaseListAdapter
import com.sigma.flick.databinding.ItemRecordsDateBinding
import com.sigma.flick.feature.myaccount.screen.FragmentBankbookRecords
import com.sigma.flick.feature.myaccount.adapter.data.RecordsDateData
import com.sigma.flick.feature.myaccount.adapter.decoration.RecordsDateItemDecoration

class RecordsDateListAdapter: BaseListAdapter<RecordsDateData,ItemRecordsDateBinding>(
    R.layout.item_records_date), BaseListAdapter.OnItemClickListener {
    override fun action(data: RecordsDateData, binding: ItemRecordsDateBinding) {
        val detailedRecordsListAdapter = DetailedRecordsListAdapter()
        binding.date.text = data.date
        val recordsDateItemDecoration = RecordsDateItemDecoration()
        binding.recyclerView.layoutManager = LinearLayoutManager(FragmentBankbookRecords.applicationContext())
        binding.recyclerView.adapter = detailedRecordsListAdapter
        binding.recyclerView.addItemDecoration(recordsDateItemDecoration)
        detailedRecordsListAdapter.submitList(data.detailedData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(ItemRecordsDateBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onClick(v: View, position: Int) {

    }

}