package com.example.android_base_2025.widget

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_base_2025.data.vo.PickerItem
import com.example.android_base_2025.databinding.CustomDialogViewBinding
import com.example.android_base_2025.ui.dialog.adapter.SearchPickerAdapter

class CustomDialog(
    private val items: List<PickerItem>
) : DialogFragment() {

    private var _binding: CustomDialogViewBinding? = null
    private val binding get() = _binding!!

    private var initData: MutableList<PickerItem> = mutableListOf()
    internal var onItemSelected: (PickerItem) -> Unit = {}
    internal var adapter: SearchPickerAdapter? = null

    init {
        reInitData(items)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())

        val binding = CustomDialogViewBinding.inflate(requireActivity().layoutInflater)
        dialog.setContentView(binding.root)

        adapter = SearchPickerAdapter(initData) { item ->
            onItemSelected(item)
            dismiss()
        }
        binding.run {
            itemRecyclerView.layoutManager = LinearLayoutManager(context)
            itemRecyclerView.adapter = adapter

            searchEditText.addTextChangedListener { text ->
                reInitData(
                    items.filter {
                        it.name.contains(text?.trim().toString(), ignoreCase = true)
                    }
                )
                adapter?.notifyDataSetChanged()
            }
        }

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        return dialog
    }

    private fun reInitData(data: List<PickerItem>) {
        initData.apply {
            clear()
            addAll(data)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
