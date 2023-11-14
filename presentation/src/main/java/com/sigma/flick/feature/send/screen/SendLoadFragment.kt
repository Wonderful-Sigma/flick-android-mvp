package com.sigma.flick.feature.send.screen

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentSendLoadBinding
import com.sigma.flick.feature.send.viewmodel.SendViewModel
import com.sigma.flick.utils.setDeleteBottomNav

class SendLoadFragment: BaseFragment<FragmentSendLoadBinding, SendViewModel>(R.layout.fragment_send_load) {

    override val viewModel: SendViewModel by activityViewModels()

    override fun start() {
        setDeleteBottomNav(activity)

        val accountName = viewModel.depositAccountName.value
        val sendCoin = viewModel.sendCoin.value.toString()

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