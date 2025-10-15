package com.example.android_base_2025.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android_base_2025.databinding.Fragment6Binding

class Fragment6 : Fragment() {

    private var _binding: Fragment6Binding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = Fragment6Binding.inflate(inflater, container, false)
        return binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }
    private fun initListener() {
        binding?.run {
            tv7.setOnClickListener {
                Log.d("vaoday", "7")
            }
//            tvK.setOnClickListener {
//                Log.d("vaoday", "K")
//            }

            tv9.setOnClickListener {
                Log.d("vaoday", "9")
            }

            tv6.setOnClickListener {
                Log.d("vaoday", "6")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}