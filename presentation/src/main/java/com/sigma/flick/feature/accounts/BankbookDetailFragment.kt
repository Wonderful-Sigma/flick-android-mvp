package com.sigma.flick.feature.accounts

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sigma.data.network.dto.account.Account
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentBankbookDetailBinding
import com.sigma.flick.feature.accounts.viewmodel.BankbookDetailViewModel
import com.sigma.flick.feature.user.viewmodel.UserViewModel
import java.text.DecimalFormat


class BankbookDetailFragment : BaseFragment<FragmentBankbookDetailBinding, BankbookDetailViewModel>(R.layout.fragment_bankbook_detail) {

    override val viewModel: BankbookDetailViewModel by viewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    override fun start() {
        var myInfo: Account
        var myCoin: Long

        userViewModel.myInfo.observe(viewLifecycleOwner) {
            myInfo = userViewModel.myInfo.value!!.account[0]
            myCoin = myInfo.money

            with(binding){
                tvMyCoinBig.text = getDecimalFormat(myCoin)
                tvMyCoin.text = getDecimalFormat(myCoin)
            }
        }

        setNavigation()

        binding.refreshLayout.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                userViewModel.getUserInfo()
                binding.refreshLayout.isRefreshing = false
            },1000)
        }
    }

    private fun setNavigation() {
        with(binding){
            ibBackArrow.setOnClickListener{
                findNavController().popBackStack()
            }
            binding.myBankbookButton.setOnClickListener {
                findNavController().navigate(R.id.action_accountDetailFragment_to_fragmentBankbookRecords)
            }
            btnSend.setOnClickListener {
                findNavController().navigate(R.id.action_accountDetailFragment_to_sendWhereFragment)
            }
        }
    }
    private fun getDecimalFormat(number: Long): String {
        val decimalFormat = DecimalFormat("#,###")
        return decimalFormat.format(number)+"코인"
    }
}