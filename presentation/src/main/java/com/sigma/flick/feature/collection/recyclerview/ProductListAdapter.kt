package com.sigma.flick.feature.collection.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sigma.flick.R

class ProductListAdapter(
    private val itemList: List<Product>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sell_product, parent, false)
        return ViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

//        holder.image. = item.date
        holder.productPrice.text = item.productPrice.toString() + "코인"
        holder.productName.text = item.productName
    }

    override fun getItemCount(): Int = itemList.size

    class ViewHolder(
        itemView: View,
        private val onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener  {

        init {
            itemView.setOnClickListener(this)
        }


        val image: ImageView = itemView.findViewById(R.id.iv_product_image)
        val productPrice: TextView = itemView.findViewById(R.id.tv_product_price)
        val productName: TextView = itemView.findViewById(R.id.tv_product_name)

        override fun onClick(p0: View?) {
            onItemClickListener.onItemClick(adapterPosition)
        }

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }


}