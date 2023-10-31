package com.sigma.flick.feature.collection.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sigma.flick.R
import com.sigma.flick.feature.collection.screen.start.CollectionDetailFragment

class UseDateAdapter(
    private val itemList: List<UseDate>
) : RecyclerView.Adapter<UseDateAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_records_date, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

        val useDetailAdapter = UseDetailAdapter(item.detailedData)
        holder.date.text = item.date

        holder.recyclerView.layoutManager = LinearLayoutManager(CollectionDetailFragment.applicationContext())
        holder.recyclerView.adapter = useDetailAdapter
    }

    override fun getItemCount(): Int = itemList.size


    class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.date)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
    }


}