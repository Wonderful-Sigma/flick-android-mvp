package com.sigma.flick.feature.send.screen

import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentSendLoadBinding
import com.sigma.flick.feature.send.viewmodel.SendViewModel
import com.sigma.flick.feature.user.viewmodel.UserViewModel
import com.sigma.flick.utils.setDeleteBottomNav
import com.sigma.main.model.account.RemitRequestModel
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
            viewModel.remit(RemitRequestModel(remittanceAccount, sendMoney, depositAccount)).join()
            userViewModel.getUserInfo()
        }



        binding.tvLoadTitle.text = "${accountName}님에게\n${sendCoin}코인을 보낼게요"

        Handler().postDelayed({
            val action = SendLoadFragmentDirections.toSendFinishFragment()
            findNavController().navigate(action)
        }, 1000L)

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    /** 뒤로가기 막기 */
                }
            })
    }

}