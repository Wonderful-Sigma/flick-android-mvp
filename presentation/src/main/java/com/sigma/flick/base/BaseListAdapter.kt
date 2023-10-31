package com.sigma.flick.base

import android.annotation.SuppressLint
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


abstract class BaseListAdapter<T : Any, VDB : ViewDataBinding>(
    @LayoutRes private val itemLayoutRes: Int
) : ListAdapter<T, BaseListAdapter<T, VDB>.BaseViewHolder>(BaseItemCallback<T>()) {

    abstract fun action(data: T, binding: VDB)

    inner class BaseViewHolder(private val binding: VDB) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T) {
            action(item, binding)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {

        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }

        return holder.bind(getItem(position))
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener

}

class BaseItemCallback<T : Any> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem.toString() == newItem.toString()

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem
}
