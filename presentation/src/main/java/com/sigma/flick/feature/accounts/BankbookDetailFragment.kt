package com.sigma.flick.feature.accounts

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentBankbookDetailBinding
import com.sigma.flick.feature.accounts.viewmodel.BankbookDetailViewModel
import com.sigma.flick.feature.tabs.home.adapter.GroupBankBookListAdapter
import com.sigma.flick.feature.user.viewmodel.UserViewModel
import java.text.DecimalFormat


class BankbookDetailFragment : BaseFragment<FragmentBankbookDetailBinding, BankbookDetailViewModel>(R.layout.fragment_bankbook_detail) {

    override val viewModel: BankbookDetailViewModel by viewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    override fun start() {
        val myInfo = userViewModel.myInfo.value!!.account[0]
        val myCoin = myInfo.money

        with(binding){
            tvMyCoinBig.text = getDecimalFormat(myCoin)
            tvMyCoin.text = getDecimalFormat(myCoin)
        }

        setNavigation()
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