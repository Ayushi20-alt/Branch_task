package com.example.branchtask.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.branchtask.databinding.HomerecyleritemBinding

import com.example.branchtask.domain.models.HomemodelItem

class SecondAdapter: ListAdapter<HomemodelItem, SecondAdapter.SecondViewHolder>(ComparatorDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecondViewHolder {
        val binding = HomerecyleritemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SecondViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SecondViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class SecondViewHolder(private val binding: HomerecyleritemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(home: HomemodelItem) {
            // Bind data to views in the second item layout
            binding.idtext.text = home.id.toString()
            binding.agentidtext.text = home.agent_id
            binding.bodytext.text = home.body
            binding.threadidtext.text = home.thread_id.toString()
            binding.timestamptext.text = home.timestamp
            binding.useridtext.text = home.user_id
        }
    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<HomemodelItem>() {
        override fun areItemsTheSame(oldItem: HomemodelItem, newItem: HomemodelItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HomemodelItem, newItem: HomemodelItem): Boolean {
            return oldItem == newItem
        }
    }
}