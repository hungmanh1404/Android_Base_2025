package com.example.android_base_2025.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android_base_2025.databinding.Fragment3Binding

class Fragment3 : Fragment() {
    private var _binding: Fragment3Binding? = null
    private val binding get() = _binding

    private val flowHost: FlowContainerFragment?
        get() = parentFragment as? FlowContainerFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = Fragment3Binding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            ctvComment.setData(flowHost?.userData?.phone ?: "")
            btnNext.setOnClickListener {
                flowHost?.userData?.phone = ctvComment.getData()
                flowHost?.completeFlow()
                activity?.supportFragmentManager?.popBackStack()
//                (parentFragment as? FlowContainerFragment)?.backTo("Fragment1", false)
            }
            btnContra.setOnClickListener {
                flowHost?.showFragment4()
            }
            btnLinearLayout.setOnClickListener {
                flowHost?.showFragment5()
            }
            btnRelativeLayout.setOnClickListener {
                flowHost?.showFragment6()
            }
        } ?: {
            Log.d("xu ly truong hop null", "")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val KEY_USER = "user"

        internal fun newInstance(userName: String) = Fragment3().apply {
            arguments = Bundle().apply {
                putString(KEY_USER, userName)
            }
        }
    }
}