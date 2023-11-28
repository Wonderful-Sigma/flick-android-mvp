package com.sigma.flick.feature.send.screen

import android.os.Handler
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentSendLoadBinding
import com.sigma.flick.feature.send.viewmodel.SendViewModel
import com.sigma.flick.main.toDecimalFormat
import com.sigma.flick.utils.setDeleteBottomNav

class SendLoadFragment :
    BaseFragment<FragmentSendLoadBinding, SendViewModel>(R.layout.fragment_send_load) {

    override val viewModel: SendViewModel by activityViewModels()

    override fun start() {
        setDeleteBottomNav(activity)

        val accountName = viewModel.depositAccountName.value
        val sendCoin = viewModel.sendCoin.value.toString()

        binding.tvLoadTitle.text = "${accountName}님에게\n${sendCoin.toDecimalFormat()}코인을 보낼게요"

        Handler().postDelayed({
            Log.d("다음","다음")
            val action = SendLoadFragmentDirections.toSendFinishFragment()
            findNavController().navigate(action)
        }, 1000L)
    }
}