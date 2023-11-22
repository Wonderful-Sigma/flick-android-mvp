package com.sigma.flick.feature.send.screen

import android.os.Handler
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sigma.data.network.dto.account.RemitRequest
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentSendLoadBinding
import com.sigma.flick.feature.send.viewmodel.SendViewModel
import com.sigma.flick.feature.user.viewmodel.UserViewModel
import com.sigma.flick.utils.setDeleteBottomNav
import kotlinx.coroutines.launch

class SendLoadFragment :
    BaseFragment<FragmentSendLoadBinding, SendViewModel>(R.layout.fragment_send_load) {

    override val viewModel: SendViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    override fun start() {
        setDeleteBottomNav(activity)

        val accountName = viewModel.depositAccountName.value
        val sendCoin = viewModel.sendCoin.value.toString()

        val remittanceAccount = userViewModel.myInfo.value!!.account[0].id
        val depositAccount = viewModel.depositAccountId.value!!
        val sendMoney = viewModel.sendCoin.value!!.toLong()

        lifecycleScope.launch{
            viewModel.remit(RemitRequest(remittanceAccount, sendMoney, depositAccount)).join()
            userViewModel.getUserInfo()
        }



        binding.tvLoadTitle.text = "${accountName}님에게\n${sendCoin}코인을 보낼게요"

        Handler().postDelayed({
            val action = SendLoadFragmentDirections.toSendFinishFragment()
            findNavController().navigate(action)
        }, 1000L)
    }

}