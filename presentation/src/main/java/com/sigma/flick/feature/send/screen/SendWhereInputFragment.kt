package com.sigma.flick.feature.send.screen

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentSendWhereInputBinding
import com.sigma.flick.feature.send.viewmodel.SendViewModel
import com.sigma.flick.utils.setDeleteBottomNav
import com.sigma.flick.utils.setPopBackStack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SendWhereInputFragment : BaseFragment<FragmentSendWhereInputBinding, SendViewModel>(R.layout.fragment_send_where_input) {

    override val viewModel: SendViewModel by activityViewModels()

    private lateinit var etNumber: String

    override fun start() {
        setDeleteBottomNav(activity)
        binding.toolbar.setPopBackStack()

        val context = requireContext() //5637-4925-5126-8

        binding.btnOk.setOnClickListener {
            etNumber = binding.etInputAccount.text.toString()
            if (etNumber.isNotEmpty()) {
                viewModel.checkAccount(etNumber)
            } else {
                Toast.makeText(requireContext(), "빈칸을 채워주세요", Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launch {
            viewModel.accountCheckState.collect {
                if (it.isSuccess) {
                    viewModel.setDepositAccountNumber(etNumber)
                    Navigation.findNavController(requireView()).navigate(SendWhereInputFragmentDirections.toSendPointFragment())
                }
                if (it.error.isNotEmpty()) {
                    Toast.makeText(context, "계좌번호를 찾지 못했어요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}