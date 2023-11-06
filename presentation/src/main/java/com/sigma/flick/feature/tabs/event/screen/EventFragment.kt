package com.sigma.flick.feature.tabs.event.screen

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentEventBinding
import com.sigma.flick.feature.tabs.event.viewmodel.EventViewModel

class EventFragment : BaseFragment<FragmentEventBinding, EventViewModel>(R.layout.fragment_event) {

    override val viewModel: EventViewModel by viewModels()
    override fun start() {
        binding.btnMyCoins.setOnClickListener {
            val action = EventFragmentDirections.toFragmentBankbookRecords()
            findNavController().navigate(action)
        }
    }
}