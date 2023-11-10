package com.sigma.flick.feature.myaccount.screen

import android.content.Context
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentBankbookRecordsBinding
import com.sigma.flick.feature.myaccount.adapter.RecordsDateListAdapter
import com.sigma.flick.feature.myaccount.adapter.data.DetailedData
import com.sigma.flick.feature.myaccount.adapter.data.RecordsDateData
import com.sigma.flick.feature.myaccount.adapter.decoration.DetailedRecordsItemDecoration
import com.sigma.flick.feature.myaccount.viewmodel.BankbookRecordsViewModel
import com.sigma.flick.feature.user.viewmodel.UserViewModel
import com.sigma.flick.utils.setStatusBarColorWhite
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
        val myAccount = userViewModel.myInfo.value!!.account[0]
        binding.tvMyAccountName.text = "내 통장"
        binding.tvMyAccountNumber.text = "대소코인 " + myAccount.number
        binding.tvMyCoinBig.text = getDecimalFormat(myAccount.money)
        viewModel.allSpend(myAccount.id)
        viewModel.getWallet(myAccount.id)

        val recordsDateListAdapter = RecordsDateListAdapter()

        val detailedRecordsItemDecoration = DetailedRecordsItemDecoration()

        binding.recyclerviewRecordsDate.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewRecordsDate.adapter = recordsDateListAdapter
        binding.recyclerviewRecordsDate.addItemDecoration(detailedRecordsItemDecoration)

        recordsDateListAdapter.setItemClickListener(recordsDateListAdapter)



        viewModel.spendList.observe(this){
            Log.d("통장",it.toString())
            val allSpendList = it
            allSpendList.map {
                val detailedData: MutableList<DetailedData> = mutableListOf()
                it.map {
                    detailedData.add(DetailedData(it.targetMember, isoToTime(it.createdDate), getDecimalFormat(it.balance), getDecimalFormat(it.money), R.drawable.ic_my))
                }
                recordsDateListData.add(RecordsDateData(isoToDate(it[0].createdDate),detailedData))
            }
            recordsDateListAdapter.submitList(recordsDateListData)
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

    // ISO 8601 형식의 문자열을 날짜로 변환하는 함수
    fun isoToDate(isoString: String): String {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        val dateTime = LocalDateTime.parse(isoString, formatter)
        return dateTime.format(DateTimeFormatter.ofPattern("MM월 dd일"))
    }

    // ISO 8601 형식의 문자열을 시간으로 변환하는 함수
    fun isoToTime(isoString: String): String {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        val dateTime = LocalDateTime.parse(isoString, formatter)
        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    }
}