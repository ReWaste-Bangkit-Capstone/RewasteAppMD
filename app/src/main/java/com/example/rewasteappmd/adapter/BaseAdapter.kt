package com.example.rewasteappmd.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BaseAdapter<VB: ViewBinding, T>(
    private val setUpViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    private val bindItemIntoLayout: (item: T, binding: VB) -> Unit
): RecyclerView.Adapter<BaseAdapter.ViewHolder<VB, T>>() {

    private var items = ArrayList<T>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newItems: List<T>?) {
        if (newItems == null) return
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    class ViewHolder<VB: ViewBinding, T>(private val binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: T, bindItemIntoLayout: (item: T, binding: VB) -> Unit) {
            @Suppress("UNCHECKED_CAST")
            bindItemIntoLayout(item, binding as VB)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<VB, T> {
        return ViewHolder(setUpViewBinding.invoke(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder<VB, T>, position: Int) {
        holder.bindItem(items[position], bindItemIntoLayout)
    }

    override fun getItemCount(): Int = items.size

}