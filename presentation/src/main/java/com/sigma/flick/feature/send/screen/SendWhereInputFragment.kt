package com.sigma.flick.feature.send.screen

import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
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

    override fun start() {
        setDeleteBottomNav(activity)
        binding.toolbar.setPopBackStack()

        val context = requireContext()
        var etNumber = ""

        binding.btnOk.setOnClickListener {
            etNumber = binding.etInputAccount.text.toString()
            if (etNumber.isNotEmpty()) {
                viewModel.checkAccountNumber(etNumber)
            }
            else {
                Toast.makeText(requireContext(), "빈칸을 채워주세요", Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launch {
            with(viewModel) {
                checkAccountState.collect {
                    if (it.isSuccess) {
                        setDepositAccountNumber(etNumber)
                        val action = SendWhereInputFragmentDirections.toSendPointFragment()
                        findNavController().navigate(action)
                    }
                    if (it.error.isNotEmpty()) {
                        Toast.makeText(context, "계좌번호를 찾지 못했어요", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}