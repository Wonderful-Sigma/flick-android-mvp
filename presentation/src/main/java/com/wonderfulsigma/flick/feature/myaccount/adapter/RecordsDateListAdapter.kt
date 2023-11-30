package com.wonderfulsigma.flick.feature.myaccount.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.wonderfulsigma.flick.R
import com.wonderfulsigma.flick.base.BaseListAdapter
import com.wonderfulsigma.flick.databinding.ItemRecordsDateBinding
import com.wonderfulsigma.flick.feature.myaccount.adapter.data.RecordsDateData
import com.wonderfulsigma.flick.feature.myaccount.adapter.decoration.RecordsDateItemDecoration
import com.wonderfulsigma.flick.feature.myaccount.screen.FragmentBankbookRecords

class RecordsDateListAdapter : BaseListAdapter<RecordsDateData, ItemRecordsDateBinding>(
    R.layout.item_records_date
), BaseListAdapter.OnItemClickListener {
    override fun action(data: RecordsDateData, binding: ItemRecordsDateBinding) {
        val detailedRecordsListAdapter = DetailedRecordsListAdapter()
        val recordsDateItemDecoration = RecordsDateItemDecoration()
        detailedRecordsListAdapter.setItemClickListener(detailedRecordsListAdapter)
        with(binding) {
            date.text = data.date
            recyclerView.layoutManager = LinearLayoutManager(FragmentBankbookRecords.applicationContext())
            recyclerView.adapter = detailedRecordsListAdapter
            recyclerView.addItemDecoration(recordsDateItemDecoration)
        }
        detailedRecordsListAdapter.submitList(data.detailedData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(
            ItemRecordsDateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onClick(v: View, position: Int) {

    }

}