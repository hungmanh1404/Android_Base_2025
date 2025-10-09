package com.example.android_base_2025

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.android_base_2025.data.vo.UserData
import com.example.android_base_2025.databinding.FragmentFlowContainerBinding

class FlowContainerFragment : Fragment() {

    private var _binding: FragmentFlowContainerBinding? = null
    private val binding get() = _binding!!
    // Dữ liệu toàn flow
    internal val userData = UserData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlowContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (childFragmentManager.findFragmentById(R.id.child_container) == null) {
            showFragment1()
        }
    }

    private fun showFragment1() {
        childFragmentManager.beginTransaction()
            .add(R.id.child_container, Fragment1())
            .addToBackStack("Fragment1")
            .commit()
    }

    internal fun showFragment2() {
        childFragmentManager.beginTransaction()
            .add(R.id.child_container, Fragment2())
            .addToBackStack("Fragment2")
            .commit()
    }

    internal fun showFragment3() {
        childFragmentManager.beginTransaction()
            .add(R.id.child_container, Fragment3())
            .addToBackStack("Fragment3")
            .commit()
    }

    internal fun goBack() {
        // chỉ pop trong childFragmentManager
        if (childFragmentManager.backStackEntryCount > 0) {
            childFragmentManager.popBackStack()
        } else {
            // nếu không còn backstack thì thoát flow
            parentFragmentManager.popBackStack()
        }
    }

    internal fun backTo(tag: String, inclusive: Boolean = false) {
        val flag = if (inclusive) FragmentManager.POP_BACK_STACK_INCLUSIVE else 0
        childFragmentManager.popBackStack(tag, flag)
    }

    internal fun completeFlow() {
        (activity as? MainActivity)?.onFlowCompleted(userData)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}