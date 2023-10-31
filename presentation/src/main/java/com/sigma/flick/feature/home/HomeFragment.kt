package com.sigma.flick.feature.home

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.messaging.FirebaseMessaging
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentHomeBinding
import com.sigma.flick.feature.home.adapter.ExplanationListAdapter
import com.sigma.flick.feature.home.adapter.GroupBankBookListAdapter
import com.sigma.flick.feature.home.adapter.data.ItemExplanationData
import com.sigma.flick.feature.home.adapter.data.ItemGroupBankBookData
import com.sigma.flick.feature.home.adapter.decoration.ExplanationItemDecoration
import com.sigma.flick.feature.home.adapter.decoration.GroupBankBookItemDecoration
import com.sigma.flick.feature.home.viewmodel.HomeViewModel
import com.sigma.flick.feature.main.MainActivity
import com.sigma.flick.feature.user.viewmodel.UserViewModel
import com.sigma.flick.feature.qrcode.QRCode
import com.sigma.flick.utils.setStatusBarColorBackground
import com.sigma.main.model.account.Account
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModels()
    val userViewModel: UserViewModel by activityViewModels()

    private lateinit var groupBankBookListAdapter: GroupBankBookListAdapter
    private lateinit var groupBankBookItemDecoration: GroupBankBookItemDecoration
    private lateinit var explanationListAdapter: ExplanationListAdapter
    private lateinit var explanationItemDecoration: ExplanationItemDecoration

    private lateinit var context: Context
    private lateinit var myAccount: Account

    override fun start() {
        context = requireContext()
        setStatusBarColorBackground(requireActivity(), context)

        groupBankBookListAdapter = GroupBankBookListAdapter()
        groupBankBookItemDecoration = GroupBankBookItemDecoration()
        explanationListAdapter = ExplanationListAdapter()
        explanationItemDecoration = ExplanationItemDecoration()

        groupBankBookListAdapter.setItemClickListener(groupBankBookListAdapter)

        setAdapter()

        observeMyInfo()

        /** QR Code */
        showQRCode(context)

        //** navigation */
        setNavigation()
    }

    private fun observeMyInfo() {
        userViewModel.myInfo.observe(viewLifecycleOwner) { myInfo ->
            val myAccountList = myInfo?.account!!
            if (myAccountList.isNotEmpty()) {
                myAccount = myAccountList[0]
                binding.tvMyAccount.text = myAccount.name
                binding.tvMyCoin.text = getDecimalFormat(myAccount.money)

                val explanationData = mutableListOf(
                    ItemExplanationData(
                        "코인 버는법",
                        "모임 통장\n만들기",
                        R.drawable.ic_add
                    ),
                    ItemExplanationData(
                        "코인 버는법",
                        "이벤트\n참여하기",
                        R.drawable.ic_box
                    ),
                )

                if (myAccountList.lastIndex > 0) {
                    userViewModel.collectionData = mutableListOf()
                    val myCollectionAccountList = myAccountList.slice(1..myAccountList.lastIndex)

                    myCollectionAccountList.forEach { collection ->
                        userViewModel.collectionData.add(
                            ItemGroupBankBookData(
                                collection.name,
                                getDecimalFormat(collection.money),
                                R.drawable.circle
                            )
                        )
                    }
                }

                groupBankBookListAdapter.submitList(userViewModel.collectionData)
                explanationListAdapter.submitList(explanationData)
            }
        }
    }

    private fun setAdapter() {
        //** Adapter Setting */
        with(binding) {
            recyclerviewGroupBankbook.layoutManager = LinearLayoutManager(context)

            recyclerviewExplanation.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            recyclerviewGroupBankbook.adapter = groupBankBookListAdapter
            recyclerviewExplanation.adapter = explanationListAdapter

            recyclerviewGroupBankbook.addItemDecoration(groupBankBookItemDecoration)
            recyclerviewExplanation.addItemDecoration(explanationItemDecoration)
        }
    }

    private fun setNavigation() {
        with(binding){
            btnSend.setOnClickListener { findNavController().navigate(R.id.to_sendWhereFragment) }
            linearMyAccount.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_fragmentBankbookRecords) }
            bankbookButton.setOnClickListener { findNavController().navigate(R.id.to_accountDetailFragment) }
            consumptionButton.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_spendFragment) }
            alarmButton.setOnClickListener {  }
        }
    }

    private fun showQRCode(context: Context) {
        val qrCodeClass = QRCode(userViewModel, context, viewLifecycleOwner, this@HomeFragment, layoutInflater)

        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(qrCodeClass.bottomSheetView)

        binding.paymentButton.setOnClickListener {
            bottomSheetDialog.show()
            qrCodeClass.generateQRCode()
            qrCodeClass.observeMyCoin() // todo : 계속 null 코인이 뜸
        }
        qrCodeClass.observeQRCode(viewLifecycleOwner)

    }

    private fun getDecimalFormat(number: Long): String {
        val decimalFormat = DecimalFormat("#,###")
        return decimalFormat.format(number)+"코인"
    }

    companion object {
        const val TAG = "HomeFragment"
    }

}