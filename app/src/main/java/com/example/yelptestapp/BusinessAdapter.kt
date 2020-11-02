package com.example.yelptestapp


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.BusinessSearchQuery
import com.example.yelptestapp.databinding.BusinessItemBinding


class BusinessAdapter() :
    ListAdapter<BusinessSearchQuery.Business, BusinessAdapter.ViewHolder>(BusinessDiffCallback()) {

    class ViewHolder private constructor(private val binding: BusinessItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = BusinessItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(business: BusinessSearchQuery.Business) {
            binding.business = business
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class BusinessDiffCallback : DiffUtil.ItemCallback<BusinessSearchQuery.Business>() {
    override fun areItemsTheSame(
        oldItem: BusinessSearchQuery.Business,
        newItem: BusinessSearchQuery.Business
    ) = oldItem === newItem

    override fun areContentsTheSame(
        oldItem: BusinessSearchQuery.Business,
        newItem: BusinessSearchQuery.Business
    ) = oldItem == newItem
}