package com.sigma.flick.feature.collection.screen.start

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentCollectionDetailBinding
import com.sigma.flick.feature.collection.recyclerview.UseDate
import com.sigma.flick.feature.collection.recyclerview.UseDateAdapter
import com.sigma.flick.feature.collection.recyclerview.UseDetail
import com.sigma.flick.feature.collection.viewmodel.CollectionViewModel
import com.sigma.flick.feature.user.viewmodel.UserViewModel
import com.sigma.main.model.account.Account
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class CollectionDetailFragment :
    BaseFragment<FragmentCollectionDetailBinding, CollectionViewModel>(R.layout.fragment_collection_detail) {

    companion object {
        lateinit var instance: CollectionDetailFragment
        fun applicationContext(): Context = instance.requireContext()
    }

    init {
        instance = this
    }

    override val viewModel: CollectionViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    private val accountData = MutableLiveData<Account>()

    private var dateDataList = mutableListOf<UseDate>()

    private val useDateAdapter = UseDateAdapter(dateDataList)


    override fun start() {
        val position = arguments?.getInt("position")
        val accountNumber = arguments?.getString("accountNumber")

        if (accountNumber != null) {
            Log.d("arguments", "accountNumber: $accountNumber")
            userViewModel.getAccount(accountNumber)
            userViewModel.accountData.observe(this) {
                accountData.value = it
            }
        } else if (position != null) {
            Log.d("arguments", "position: $position")
            accountData.value = userViewModel.myInfo.value?.account?.get(position + 1)!!
        } else {
            Toast.makeText(context, "애러", Toast.LENGTH_SHORT).show()
        }


        accountData.observe(this) {
            with(binding) {
                Log.d("모임 통장",it.toString())
                viewModel.getSpend(it.id)
                tvCollectionName.text = it.name
                tvHaveCoin.text = getDecimalFormat(it.money)
                tvAccountNumber.text = "대소코인 " + it.number
            }
        }
        viewModel.spendList.observe(this@CollectionDetailFragment) { spendData ->
            Log.d("모임 통장",spendData.toString())
            // 데이터 처리 코드
            spendData.forEach {
                val dateTimeString = it[0].createdDate
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
                val dateTime = LocalDateTime.parse(dateTimeString, formatter)

                val month = dateTime.monthValue.toString()+"월"
                val day = dateTime.dayOfMonth.toString()+"일"
                val date = "$month $day"

                var spend: MutableList<UseDetail> = mutableListOf()
                for(spendData in it){
                    spend.add(UseDetail(spendData.targetMember, time = spendData.createdDate, changedMoney = spendData.balance.toString(), currentMoney = spendData.money.toString(), profileIcon = 0, spendType = spendData.spendType))
                }
                dateDataList.add(UseDate(date, spend))

                useDateAdapter.notifyDataSetChanged()
            }


        }

        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = useDateAdapter
            btnSell.setOnClickListener { findNavController().navigate(R.id.to_collectionSettingSellFragment) }
            btnBackArrow.setOnClickListener { findNavController().navigate(R.id.action_collectionDetailFragment_to_homeFragment) }
            btnSetting.setOnClickListener { findNavController().navigate(R.id.to_collectionSettingFragment) }
        }

    }
}

private fun getDecimalFormat(number: Long): String {
    val decimalFormat = DecimalFormat("#,###")
    return decimalFormat.format(number) + "코인"
}