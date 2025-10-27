package com.example.android_base_2025.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android_base_2025.databinding.Fragment1Binding

class Fragment1 : Fragment() {
    private var _binding: Fragment1Binding? = null
    private val binding get() = _binding

    private val flowHost: FlowContainerFragment?
        get() = parentFragment as? FlowContainerFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = Fragment1Binding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {
            ctvComment.setData(flowHost?.userData?.name ?: "")
            btnNext.setOnClickListener {
                flowHost?.userData?.name = ctvComment.getData()
                flowHost?.showFragment2()

//            requireActivity().supportFragmentManager.beginTransaction()
//                .add(R.id.fragment_container, Fragment2())
//                .addToBackStack("Fragment2") // thêm vào backstack để có thể "Back"
//                .commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        internal fun newInstance() = Fragment1().apply {
            arguments = Bundle().apply {

            }
        }
    }
}