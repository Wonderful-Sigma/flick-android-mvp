package com.wonderfulsigma.flick.feature.myaccount.screen

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.wonderfulsigma.flick.R
import com.wonderfulsigma.flick.base.BaseFragment
import com.wonderfulsigma.flick.databinding.FragmentBankbookRecordsBinding
import com.wonderfulsigma.flick.feature.myaccount.adapter.RecordsDateListAdapter
import com.wonderfulsigma.flick.feature.myaccount.adapter.data.DetailedData
import com.wonderfulsigma.flick.feature.myaccount.adapter.data.RecordsDateData
import com.wonderfulsigma.flick.feature.myaccount.adapter.decoration.DetailedRecordsItemDecoration
import com.wonderfulsigma.flick.feature.myaccount.viewmodel.BankbookRecordsViewModel
import com.wonderfulsigma.flick.feature.user.viewmodel.UserViewModel
import com.wonderfulsigma.flick.main.toDecimalFormat
import com.wonderfulsigma.flick.utils.setStatusBarColorWhite
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class FragmentBankbookRecords :
    BaseFragment<FragmentBankbookRecordsBinding, BankbookRecordsViewModel>(R.layout.fragment_bankbook_records) {

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
            binding.tvMyCoinBig.text = myAccount.money.toDecimalFormat()
        }

        binding.tvMyAccountName.text = "내 통장"
        binding.tvMyAccountNumber.text = "대소코인 " + myAccount.number

        viewModel.allSpend(myAccount.id)
        viewModel.getWallet(myAccount.id)

        val recordsDateListAdapter = RecordsDateListAdapter()
        recordsDateListAdapter.setItemClickListener(recordsDateListAdapter)

        val detailedRecordsItemDecoration = DetailedRecordsItemDecoration()

        /** Set RecyclerView */
        binding.recyclerviewRecordsDate.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewRecordsDate.adapter = recordsDateListAdapter
        binding.recyclerviewRecordsDate.addItemDecoration(detailedRecordsItemDecoration)


        /** Spend List */
        viewModel.spendList.observe(this) {
            val allSpendList = it
            recordsDateListData = mutableListOf()
            allSpendList.map { spendListData ->
                val detailedData: MutableList<DetailedData> = mutableListOf()
                spendListData.map { spendData ->
                    detailedData.add(
                        DetailedData(
                            spendData.targetMember,
                            isoToTime(spendData.createdDate),
                            spendData.balance.toDecimalFormat(),
                            spendData.money.toDecimalFormat(),
                            R.drawable.ic_my
                        )
                    )
                }
                recordsDateListData.add(
                    RecordsDateData(
                        isoToDate(spendListData[0].createdDate),
                        detailedData
                    )
                )
            }
            recordsDateListAdapter.submitList(recordsDateListData)
        }


        /** Navigation */

        bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetFill: View = layoutInflater.inflate(R.layout.layout_bottom_sheet, null)

        bottomSheetDialog.setContentView(bottomSheetFill)

        with(binding) {
            btnBackArrow.setOnClickListener { findNavController().popBackStack() }
            btnSend.setOnClickListener {
                val action =
                    FragmentBankbookRecordsDirections.actionFragmentBankbookRecordsToSendWhereFragment()
                findNavController().navigate(action)
            }
        }

        binding.home.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                userViewModel.getUserInfo()
                binding.home.isRefreshing = false
            }, 1000)
        }
    }

    private fun isoToDate(isoString: String): String {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        val dateTime = LocalDateTime.parse(isoString, formatter)
        return dateTime.format(DateTimeFormatter.ofPattern("MM월 dd일"))
    }

    private fun isoToTime(isoString: String): String {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        val dateTime = LocalDateTime.parse(isoString, formatter)
        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    }
}