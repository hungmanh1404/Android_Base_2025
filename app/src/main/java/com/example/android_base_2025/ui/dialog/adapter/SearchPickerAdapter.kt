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

    private var filteredItems: MutableList<PickerItem> = items.toMutableList()

    inner class ViewHolder(private val binding: ItemCustomDialogBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PickerItem) {
            binding.run {
                textView.text = item.name
                iconView.visibility = if (item.isSelected) {
                    android.view.View.VISIBLE
                } else {
                    android.view.View.GONE
                }

                viewItemCustomDialog.setOnClickListener {
                    items.forEach { it.isSelected = false }
                    filteredItems.forEach { it.isSelected = false }
                    item.isSelected = true
                    notifyDataSetChanged()
                    onItemClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCustomDialogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = filteredItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredItems[position])
    }

    fun filter(query: String) {
        filteredItems = if (query.isEmpty()) {
            items.toMutableList()
        } else {
            items.filter { it.name.contains(query, ignoreCase = true) }.toMutableList()
        }
        notifyDataSetChanged()
    }
}
