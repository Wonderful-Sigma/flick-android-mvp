package com.sigma.flick.feature.tabs.event.screen

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentEventBinding
import com.sigma.flick.feature.tabs.event.viewmodel.EventViewModel
import com.sigma.flick.feature.user.viewmodel.UserViewModel

class EventFragment : BaseFragment<FragmentEventBinding, EventViewModel>(R.layout.fragment_event) {

    override val viewModel: EventViewModel by viewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    override fun start() {
        binding.tvMyCoin.text = userViewModel.myInfo.value!!.account[0].money.toString() + "원"

        userViewModel.myInfo.observe(viewLifecycleOwner) {
            binding.tvMyCoin.text = userViewModel.myInfo.value!!.account[0].money.toString() + "원"
        }

        binding.btnMyCoins.setOnClickListener {
            val action = EventFragmentDirections.toFragmentBankbookRecords()
            findNavController().navigate(action)
        }
    }
}