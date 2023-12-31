package com.wonderfulsigma.flick.feature.tabs.event.screen

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.wonderfulsigma.flick.R
import com.wonderfulsigma.flick.base.BaseFragment
import com.wonderfulsigma.flick.databinding.FragmentEventBinding
import com.wonderfulsigma.flick.feature.tabs.event.viewmodel.EventViewModel
import com.wonderfulsigma.flick.feature.user.viewmodel.UserViewModel
import com.wonderfulsigma.flick.main.toDecimalFormat

class EventFragment : BaseFragment<FragmentEventBinding, EventViewModel>(R.layout.fragment_event) {

    override val viewModel: EventViewModel by viewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    override fun start() {
        binding.btnMyCoins.setOnClickListener {
            val action = EventFragmentDirections.toFragmentBankbookRecords()
            findNavController().navigate(action)
        }

        userViewModel.myInfo.observe(viewLifecycleOwner) { myInfo ->
            val myAccount = myInfo?.account!![0]
            binding.tvMyCoin.text = myAccount.money.toDecimalFormat()
        }
    }
}