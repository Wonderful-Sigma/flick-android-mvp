package com.sigma.flick.feature.bankbook_records

import android.content.Context
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
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
import com.sigma.flick.feature.myaccount.screen.FragmentBankbookRecordsDirections
import com.sigma.flick.feature.myaccount.viewmodel.BankbookRecordsViewModel
import com.sigma.flick.feature.user.viewmodel.UserViewModel
import com.sigma.flick.utils.setStatusBarColorWhite
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentBankbookRecords : BaseFragment<FragmentBankbookRecordsBinding, BankbookRecordsViewModel>(R.layout.fragment_bankbook_records) {

    override val viewModel: BankbookRecordsViewModel by viewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    companion object {
        lateinit var instance: FragmentBankbookRecords

        fun ApplicationContext(): Context {
            return instance.requireContext()
        }
    }

    init {
        instance = this
    }

    private var recordsDateListData = mutableListOf(
        RecordsDateData("8월 11일",
            mutableListOf(
                DetailedData("이석호","09:22","0원","-1,600코인",R.drawable.ic_my),
                DetailedData("이석호","09:22","1,600원","1,600코인",R.drawable.ic_my)
            )
        ),
        RecordsDateData("8월 12일", mutableListOf(DetailedData("이석호","09:22","0원","-1,600코인",R.drawable.ic_my),DetailedData("이석호","09:22","1,600원","1,600코인",R.drawable.ic_my))),
        RecordsDateData("8월 13일", mutableListOf(DetailedData("이석호","09:22","0원","-1,600코인",R.drawable.ic_my),DetailedData("이석호","09:22","1,600원","1,600코인",R.drawable.ic_my))),
        RecordsDateData("8월 14일", mutableListOf(DetailedData("이석호","09:22","0원","-1,600코인",R.drawable.ic_my),DetailedData("이석호","09:22","1,600원","1,600코인",R.drawable.ic_my))),
    )

    private lateinit var bottomSheetDialog: BottomSheetDialog

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val recyclerViewFillBottomSheet = view.findViewById<RecyclerView>(R.id.recyclerViewFillBottomSheet)!!
//
//        bottomSheetDialog = BottomSheetDialog(requireContext())
//        val bottomSheetFill: View = layoutInflater.inflate(R.layout.layout_fill_bottom_sheet, null)
//
//        val groupBankBookListAdapter = GroupBankBookListAdapter()
//
//        groupBankBookListAdapter.submitList(userViewModel.collectionData)
//        groupBankBookListAdapter.setItemClickListener(groupBankBookListAdapter)
//
//        recyclerViewFillBottomSheet.layoutManager = LinearLayoutManager(context)
//        recyclerViewFillBottomSheet.adapter = groupBankBookListAdapter
//        //binding.recyclerviewGroupBankbook.addItemDecoration(explanationItemDecoration)
//
//        bottomSheetDialog.setContentView(bottomSheetFill)
//    }

    override fun start() {
        setStatusBarColorWhite(requireActivity(), requireContext())

        /** Set My Info */
        val myAccount = userViewModel.myInfo.value!!.account[0]
        binding.tvMyAccountName.text = "내 통장"
        binding.tvMyAccountNumber.text = "대소코인 " + myAccount.number
        binding.tvMyCoinBig.text = myAccount.money.toString() + "코인"


        /** Set RecyclerView */
        recordsDateListData = mutableListOf() // 특정 날짜의 리스트

        // todo : 내 계좌 리스트 갖고 오기
//        myAccount.spendLists.asReversed().forEach { spendCoin ->
//            val dateTime = spendCoin.createdDate.toString().slice(0..9)
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
        val bottomSheetFill: View = layoutInflater.inflate(R.layout.layout_fill_bottom_sheet, null)

        bottomSheetDialog.setContentView(bottomSheetFill)

        with(binding){
            btnFill.setOnClickListener {
                bottomSheetDialog.show()
            }
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
}