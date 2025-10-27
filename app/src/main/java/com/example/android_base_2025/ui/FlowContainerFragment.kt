package com.example.android_base_2025.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.android_base_2025.MainActivity
import com.example.android_base_2025.R
import com.example.android_base_2025.data.vo.UserData
import com.example.android_base_2025.databinding.FragmentFlowContainerBinding

class FlowContainerFragment : Fragment() {

    private var _binding: FragmentFlowContainerBinding? = null
    private val binding get() = _binding!!

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
            .add(R.id.child_container, Fragment1.newInstance())
            .addToBackStack("Fragment1")
            .commit()
    }

    internal fun showFragment2() {
        childFragmentManager.beginTransaction()
            .add(R.id.child_container, Fragment2.newInstance())
            .addToBackStack("Fragment2")
            .commit()
    }

    internal fun showFragment3(userName: String) {
        childFragmentManager.beginTransaction()
            .add(R.id.child_container, Fragment3.newInstance(userName = userName))
            .addToBackStack("Fragment3")
            .commit()
    }

    internal fun showFragment4() {
        childFragmentManager.beginTransaction()
            .add(R.id.child_container, Fragment4.newInstance())
            .addToBackStack("Fragment4")
            .commit()
    }

    internal fun showFragment5() {
        childFragmentManager.beginTransaction()
            .add(R.id.child_container, Fragment5.newInstance())
            .addToBackStack("Fragment5")
            .commit()
    }

    internal fun showFragment6() {
        childFragmentManager.beginTransaction()
            .add(R.id.child_container, Fragment6.newInstance())
            .addToBackStack("Fragment6")
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