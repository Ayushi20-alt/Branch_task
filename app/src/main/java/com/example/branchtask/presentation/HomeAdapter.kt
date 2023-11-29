package com.example.branchtask.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.branchtask.databinding.HomerecyleritemBinding
import com.example.branchtask.domain.models.HomemodelItem

class HomeAdapter(private val onItemClicked: (HomemodelItem) -> Unit) : ListAdapter<HomemodelItem, HomeAdapter.HomeViewHolder>(ComparatorDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = HomerecyleritemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val note = getItem(position)
        note?.let {
            holder.bind(it)
        }

    }

    inner class HomeViewHolder(private val binding: HomerecyleritemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(home : HomemodelItem) {
            binding.idtext.text = home.id.toString()
            binding.agentidtext.text = home.agent_id
            binding.bodytext.text = home.body
            binding.threadidtext.text = home.thread_id.toString()
            binding.timestamptext.text = home.timestamp
            binding.useridtext.text = home.user_id
            binding.root.setOnClickListener {
                onItemClicked(home)
            }
        }

    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<HomemodelItem>() {
        override fun areItemsTheSame(oldItem: HomemodelItem, newItem: HomemodelItem): Boolean {
            return oldItem.id== newItem.id
        }

        override fun areContentsTheSame(oldItem: HomemodelItem, newItem: HomemodelItem): Boolean {
            return oldItem == newItem
        }
    }
}