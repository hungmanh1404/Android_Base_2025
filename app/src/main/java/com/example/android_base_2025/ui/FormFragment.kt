package com.example.android_base_2025.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.android_base_2025.data.local.UserFormRepository
import com.example.android_base_2025.databinding.FragmentFormBinding
import kotlinx.coroutines.launch

class FormFragment : Fragment() {

    private var _binding: FragmentFormBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: UserFormRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = UserFormRepository(requireContext())
        binding.run {
            btnSave.setOnClickListener {
                val name = edtName.text?.toString()?.trim().orEmpty()
                val email = edtEmail.text?.toString()?.trim().orEmpty()
                val comment = edtComment.text?.toString()?.trim().orEmpty()
                val address = edtAddress.text?.toString()?.trim().orEmpty()

                if (name.isNotEmpty() && email.isNotEmpty()) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        repository.add(name, email, comment, address)
                        parentFragmentManager.popBackStack()
                    }
                } else {
                    tilName.error = if (name.isEmpty()) "Required" else null
                    tilEmail.error = if (email.isEmpty()) "Required" else null
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
