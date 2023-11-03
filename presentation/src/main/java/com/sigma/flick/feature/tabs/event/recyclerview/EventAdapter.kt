package com.sigma.flick.feature.tabs.event.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sigma.flick.R
import com.sigma.flick.base.BaseListAdapter
import com.sigma.flick.databinding.ItemEventBinding
import com.sigma.flick.feature.collection.recyclerview.UseDateAdapter

class EventAdapter(private val eventList: List<EventData>) :
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    inner class ViewHolder(
        val binding: ItemEventBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EventData) {
            binding.icon.setImageResource(item.icon)
            binding.title.text = item.title
            binding.detail.text = item.detail
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = eventList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(eventList[position])
    }
}