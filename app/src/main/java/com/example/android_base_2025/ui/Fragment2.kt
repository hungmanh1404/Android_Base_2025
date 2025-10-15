package com.example.android_base_2025.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android_base_2025.databinding.Fragment2Binding

class Fragment2 : Fragment() {
    private var _binding: Fragment2Binding? = null
    private val binding get() = _binding

    private val flowHost: FlowContainerFragment?
        get() = parentFragment as? FlowContainerFragment


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = Fragment2Binding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {
            ctvComment.setData(flowHost?.userData?.address ?: "")
            btnNext.setOnClickListener {
                flowHost?.userData?.address = ctvComment.getData()
                flowHost?.showFragment3()
            }
        }

//        binding?.btnNext?.setOnClickListener {
//            requireActivity().supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, Fragment3())
//                .addToBackStack(null) // thêm vào backstack để có thể "Back"
//                .commit()
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}