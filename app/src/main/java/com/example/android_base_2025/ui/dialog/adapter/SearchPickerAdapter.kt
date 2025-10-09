package com.example.android_base_2025.ui.dialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_base_2025.data.vo.PickerItem
import com.example.android_base_2025.databinding.ItemCustomDialogBinding

class SearchPickerAdapter(
    private var items: List<PickerItem>,
    private val onItemClick: (PickerItem) -> Unit
) : RecyclerView.Adapter<SearchPickerAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemCustomDialogBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.viewItemCustomDialog.setOnClickListener {
                onItemClick(items[bindingAdapterPosition])
            }
        }

        fun bind(item: PickerItem) {
            binding.run {
                textView.text = item.name
                iconView.visibility = if (item.isSelected) {
                    android.view.View.VISIBLE
                } else {
                    android.view.View.GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCustomDialogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
