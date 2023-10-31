package com.sigma.flick.feature.spend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentSettingBinding
import com.sigma.flick.databinding.FragmentSpendBinding
import com.sigma.flick.utils.setPopBackStack

class SpendFragment: BaseFragment<FragmentSpendBinding, SpendViewModel>(R.layout.fragment_spend) {

    override val viewModel: SpendViewModel by viewModels()

    override fun start() {
        binding.toolbar.setPopBackStack()

    }

}