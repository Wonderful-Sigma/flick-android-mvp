package com.sigma.flick.feature.collection.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sigma.flick.R
import java.text.SimpleDateFormat

class UseDetailAdapter(
    private val itemList: List<UseDetail>
) : RecyclerView.Adapter<UseDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_account_use_detail, parent, false)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

        holder.name.text = item.name
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        val outputFormat = SimpleDateFormat("HH:mm")

        val date = inputFormat.parse(item.time)
        val formattedTime = outputFormat.format(date)
        holder.time.text = formattedTime
        if(item.spendType == "EXPENDITURE"){
            holder.changedMoney.setTextColor(android.graphics.Color.parseColor("#353C49"))
            holder.currentMoney.text = "-"+item.currentMoney+"코인"
        } else {
            holder.currentMoney.text = item.currentMoney+"코인"
        }
        holder.changedMoney.text = item.changedMoney+"코인"
        holder.profileIcon.setImageResource(item.profileIcon)
    }

    override fun getItemCount(): Int = itemList.size


    class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.tv_account_name)
        val time: TextView = itemView.findViewById(R.id.tv_use_time)
        val currentMoney: TextView = itemView.findViewById(R.id.tv_use_coin)
        val changedMoney: TextView = itemView.findViewById(R.id.tv_left_coin)
        val profileIcon: ImageView = itemView.findViewById(R.id.iv_account)
    }


}