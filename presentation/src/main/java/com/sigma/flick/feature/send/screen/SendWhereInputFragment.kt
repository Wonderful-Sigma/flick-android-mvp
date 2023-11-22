package com.sigma.flick.feature.send.screen

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentSendWhereInputBinding
import com.sigma.flick.feature.send.viewmodel.SendViewModel
import com.sigma.flick.utils.setDeleteBottomNav
import com.sigma.flick.utils.setPopBackStack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SendWhereInputFragment : BaseFragment<FragmentSendWhereInputBinding, SendViewModel>(R.layout.fragment_send_where_input) {

    override val viewModel: SendViewModel by activityViewModels()

    override fun start() {
        setDeleteBottomNav(activity)
        binding.toolbar.setPopBackStack()

        val context = requireContext() //5637-4925-5126-8

        binding.btnOk.setOnClickListener {
            val etNumber = binding.etInputAccount.text.toString()
            if (etNumber.isNotEmpty()) {
                viewModel.setDepositAccountNumber(etNumber)
                findNavController().navigate(R.id.to_sendPointFragment)
            }
            else {
                Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show()
            }
        }
    }
}