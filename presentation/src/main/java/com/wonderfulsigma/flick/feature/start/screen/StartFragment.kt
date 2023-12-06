package com.wonderfulsigma.flick.feature.start.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.wonderfulsigma.flick.R
import com.wonderfulsigma.flick.base.BaseFragment
import com.wonderfulsigma.flick.databinding.FragmentStartBinding
import com.wonderfulsigma.flick.feature.start.StartViewModel
import com.wonderfulsigma.flick.utils.HiltApplication
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment: BaseFragment<FragmentStartBinding, StartViewModel>(R.layout.fragment_start) {

    override val viewModel: StartViewModel by viewModels()

    override fun start() {
        if (HiltApplication.prefs.autoLogin) {
            val action = StartFragmentDirections.toHomeFragment()
            findNavController().navigate(action)
        }
        binding.btnDauth.setOnClickListener {
            val action = StartFragmentDirections.toLoginFragment()
            findNavController().navigate(action)
        }
    }

}