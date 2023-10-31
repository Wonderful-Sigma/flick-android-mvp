package com.sigma.flick.feature.send.recyclerview.recent

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sigma.flick.R


class RecentAccountAdapter(
    private val itemList: List<RecentAccount>,
    private val onRecentAccountItemClickListener: OnRecentAccountItemClickListener
) : RecyclerView.Adapter<RecentAccountAdapter.ViewHolder>() {


    // ViewHolder 초기화
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_account, parent, false)

        return ViewHolder(view, onRecentAccountItemClickListener)
    }

    // 값 적용
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

        holder.accountName.text = item.accountName
        holder.accountNumber.text = item.accountNumber
        holder.starIcon.setImageResource(item.starIcon)
    }

    override fun getItemCount(): Int = itemList.size

    class ViewHolder(
        itemView: View,
        private val onItemClickListener: OnRecentAccountItemClickListener
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

//        val accountImage: ImageView = itemView.findViewById(R.id.iv_account)
        val accountName: TextView = itemView.findViewById(R.id.tv_account_name)
        val accountNumber: TextView = itemView.findViewById(R.id.tv_account_number)
        val starIcon: ImageView = itemView.findViewById(R.id.iv_btn_star)

        override fun onClick(p0: View?) {
            onItemClickListener.onRecentAccountItemClick(adapterPosition)
        }
    }

    interface OnRecentAccountItemClickListener {
        fun onRecentAccountItemClick(position: Int)
    }


}