package com.sigma.flick.feature.myaccount.screen

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentBankbookRecordsBinding
import com.sigma.flick.feature.myaccount.adapter.RecordsDateListAdapter
import com.sigma.flick.feature.myaccount.adapter.data.RecordsDateData
import com.sigma.flick.feature.myaccount.adapter.decoration.DetailedRecordsItemDecoration
import com.sigma.flick.feature.myaccount.viewmodel.BankbookRecordsViewModel
import com.sigma.flick.feature.user.viewmodel.UserViewModel
import com.sigma.flick.utils.setStatusBarColorWhite
import com.sigma.main.model.account.Account
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class FragmentBankbookRecords : BaseFragment<FragmentBankbookRecordsBinding, BankbookRecordsViewModel>(R.layout.fragment_bankbook_records) {

    override val viewModel: BankbookRecordsViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    companion object {
        lateinit var instance: FragmentBankbookRecords

        fun applicationContext(): Context {
            return instance.requireContext()
        }
    }

    init {
        instance = this
    }

    private var recordsDateListData: MutableList<RecordsDateData> = mutableListOf()

    private lateinit var bottomSheetDialog: BottomSheetDialog

    override fun start() {
        setStatusBarColorWhite(requireActivity(), requireContext())

        /** Set My Info */

        var myAccount = userViewModel.myInfo.value!!.account[0]

        userViewModel.myInfo.observe(viewLifecycleOwner) {
            myAccount = userViewModel.myInfo.value!!.account[0]
            binding.tvMyCoinBig.text = getDecimalFormat(myAccount.money)
        }

        binding.tvMyAccountName.text = "내 통장"
        binding.tvMyAccountNumber.text = "대소코인 ${myAccount.number}"

        viewModel.allSpend(myAccount.id)
        viewModel.getWallet(myAccount.id)

        /** Spend List */

        viewModel.spendList.observe(this){
//            val allSpendList = it
        }

        /** Set RecyclerView */
        recordsDateListData = mutableListOf() // 특정 날짜의 리스트

//        myAccount.spendLists.asReversed().forEach { spendCoin ->
//            val dateTime = spendCoin.createdDate.slice(0..9)
//
//            makeNewRecordsDateData(dateTime)
//
//            val detailedData = DetailedData(
//                spendCoin.targetMember,
//                dateTime,
//                spendCoin.balance.toString() + "코인",
//                spendCoin.money.toString() + "코인",
//                R.drawable.ic_my_large
//            )
//
//            recordsDateListData.forEach { recordsDateData ->
//                if (recordsDateData.date == detailedData.time) {
//                    recordsDateData.detailedData.add(detailedData)
//                }
//            }
//        }

        val recordsDateListAdapter = RecordsDateListAdapter()

        recordsDateListAdapter.submitList(recordsDateListData)
        recordsDateListAdapter.setItemClickListener(recordsDateListAdapter)

        val detailedRecordsItemDecoration = DetailedRecordsItemDecoration()

        binding.recyclerviewRecordsDate.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewRecordsDate.adapter = recordsDateListAdapter
        binding.recyclerviewRecordsDate.addItemDecoration(detailedRecordsItemDecoration)


        /** Navigation */

        bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetFill: View = layoutInflater.inflate(R.layout.layout_bottom_sheet, null)

        bottomSheetDialog.setContentView(bottomSheetFill)

        with(binding){
            btnBackArrow.setOnClickListener { findNavController().popBackStack() }
            btnSend.setOnClickListener {
                val action = FragmentBankbookRecordsDirections.actionFragmentBankbookRecordsToSendWhereFragment()
                findNavController().navigate(action)
            }
        }

        binding.home.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                userViewModel.getUserInfo()
                binding.home.isRefreshing = false
            },1000)
        }
    }

    private fun makeNewRecordsDateData(dateTime: String) {
        if (recordsDateListData.isEmpty()) {
            recordsDateListData.add(
                RecordsDateData(
                    dateTime,
                    mutableListOf()
                )
            )
        }
        var isSame = 0
        recordsDateListData.forEach { recordsDateData ->
            if (recordsDateData.date == dateTime) {
                isSame = 1
            }
        }
        if (isSame == 0) {
            recordsDateListData.add(
                RecordsDateData(
                    dateTime,
                    mutableListOf()
                )
            )
        }
    }
    private fun getDecimalFormat(number: Long): String {
        val decimalFormat = DecimalFormat("#,###")
        return decimalFormat.format(number)+"코인"
    }
}