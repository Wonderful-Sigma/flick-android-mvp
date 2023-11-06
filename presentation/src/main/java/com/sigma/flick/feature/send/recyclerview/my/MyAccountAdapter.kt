package com.sigma.flick.feature.send.recyclerview.my

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sigma.flick.R

class MyAccountAdapter(
    private val itemList: List<MyAccount>,
    private val onMyAccountItemClickListener: OnMyAccountItemClickListener
) : RecyclerView.Adapter<MyAccountAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_account, parent, false)

        return ViewHolder(view, onMyAccountItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

        holder.accountName.text = item.accountName
        holder.accountNumber.text = item.accountNumber
    }

    override fun getItemCount(): Int = itemList.size


    class ViewHolder(
        itemView: View,
        private val onItemClickListener: OnMyAccountItemClickListener
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init { itemView.setOnClickListener(this) }

//        val accountImage: ImageView = itemView.findViewById(R.id.iv_account)
        val accountName: TextView = itemView.findViewById(R.id.tv_account_name)
        val accountNumber: TextView = itemView.findViewById(R.id.tv_account_number)

        override fun onClick(p0: View?) {
            onItemClickListener.onMyAccountItemClick(adapterPosition)
        }
    }

    interface OnMyAccountItemClickListener {
        fun onMyAccountItemClick(position: Int)
    }


}