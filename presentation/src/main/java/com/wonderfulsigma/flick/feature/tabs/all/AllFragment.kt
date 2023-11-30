package com.wonderfulsigma.flick.feature.tabs.all

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.wonderfulsigma.flick.R
import com.wonderfulsigma.flick.base.BaseFragment
import com.wonderfulsigma.flick.databinding.FragmentAllBinding
import com.wonderfulsigma.flick.utils.setStatusBarColorWhite

class AllFragment: BaseFragment<FragmentAllBinding, AllViewModel>(R.layout.fragment_all) {

    override val viewModel: AllViewModel by viewModels()

    override fun start() {
        setStatusBarColorWhite(requireActivity(), requireContext())

        binding.ibSetting.setOnClickListener {
            val action = AllFragmentDirections.actionAllFragmentToSettingFragment()
            findNavController().navigate(action)
        }

    }

}