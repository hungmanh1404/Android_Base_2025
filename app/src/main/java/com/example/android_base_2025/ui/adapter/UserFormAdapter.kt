package com.example.android_base_2025.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_base_2025.data.local.UserFormEntity
import com.example.android_base_2025.databinding.ItemUserFormBinding

class UserFormAdapter(
    private val onDeleteClick: (UserFormEntity) -> Unit
) : ListAdapter<UserFormEntity, UserFormAdapter.VH>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<UserFormEntity>() {
            override fun areItemsTheSame(oldItem: UserFormEntity, newItem: UserFormEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UserFormEntity, newItem: UserFormEntity): Boolean =
                oldItem == newItem
        }
    }

    inner class VH(private val binding: ItemUserFormBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserFormEntity) {
            with(binding){
                tvTitle.text = item.name
                tvSubtitle.text = item.email
                tvComment.text = item.comment
                tvAddress.text = item.address
                btnDelete.setOnClickListener { onDeleteClick(item) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemUserFormBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
}
