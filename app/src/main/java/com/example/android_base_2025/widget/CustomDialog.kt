package com.example.android_base_2025.widget

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_base_2025.data.vo.PickerItem
import com.example.android_base_2025.databinding.CustomDialogViewBinding
import com.example.android_base_2025.ui.dialog.adapter.SearchPickerAdapter

class CustomDialog(
    private val items: List<PickerItem>,
    private val onItemSelected: (PickerItem) -> Unit
) : DialogFragment() {

    private var _binding: CustomDialogViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        _binding = CustomDialogViewBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)

        val adapter = SearchPickerAdapter(items) { item ->
            onItemSelected(item)
            dismiss()
        }
        binding.run {
            itemRecyclerView.layoutManager = LinearLayoutManager(context)
            itemRecyclerView.adapter = adapter
            searchEditText.addTextChangedListener { text ->
                adapter.filter(text.toString())
            }
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
