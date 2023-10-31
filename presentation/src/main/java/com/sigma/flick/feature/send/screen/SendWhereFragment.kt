package com.sigma.flick.feature.send.screen

import android.content.Context
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentSendWhereBinding
import com.sigma.flick.feature.send.recyclerview.my.MyAccount
import com.sigma.flick.feature.send.recyclerview.my.MyAccountAdapter
import com.sigma.flick.feature.send.recyclerview.recent.RecentAccount
import com.sigma.flick.feature.send.recyclerview.recent.RecentAccountAdapter
import com.sigma.flick.feature.send.viewmodel.SendViewModel
import com.sigma.flick.feature.user.viewmodel.UserViewModel
import com.sigma.flick.utils.fadeIn
import com.sigma.flick.utils.setDeleteBottomNav
import com.sigma.flick.utils.setPopBackStack
import com.sigma.flick.utils.setStatusBarColorWhite
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SendWhereFragment : BaseFragment<FragmentSendWhereBinding, SendViewModel>(R.layout.fragment_send_where), MyAccountAdapter.OnMyAccountItemClickListener, RecentAccountAdapter.OnRecentAccountItemClickListener {

    override val viewModel: SendViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    private lateinit var context: Context

    private val myCollectionAccountList: MutableList<MyAccount> = mutableListOf()

    // todo : It Will Change
    private var recentAccountList: MutableList<RecentAccount> = mutableListOf(
//        RecentAccount("이석호", "2414", R.drawable.ic_star),
//        RecentAccount("조승완", "2415", R.drawable.ic_star),
//        RecentAccount("노주완", "2300", R.drawable.ic_star),
//        RecentAccount("조승완", "2415", R.drawable.ic_star),
//        RecentAccount("노주완", "2300", R.drawable.ic_star),
//        RecentAccount("이석호", "2415", R.drawable.ic_star),
    )

    override fun start() {
        setDeleteBottomNav(activity)
        binding.toolbar.setPopBackStack()
        setStatusBarColorWhite(requireActivity(), requireContext())

        context = requireContext()

        /** Recent Spend List */

        getRecentAccount()


        val myAccountListLength = userViewModel.myInfo.value!!.account.lastIndex
        if (myAccountListLength == 0) {
            binding.linearMyAccounts.visibility = View.GONE
        }

        binding.tvMyAccountCount.text = "+" + myAccountListLength.toString() + "개"

        var myAccountListIsOpened = false

        binding.linearMyAccounts.setOnClickListener {
            myAccountListIsOpened = when (myAccountListIsOpened) {
                true -> {
                    binding.recyclerviewMyAccounts.visibility = View.GONE
                    binding.tvMyAccountCount.text = "+" + myAccountListLength.toString() + "개"
                    binding.ivArrowMini.setImageResource(R.drawable.ic_arrow_mini)
                    false
                }
                false -> {
//                    binding.recyclerviewMyAccounts.visibility = View.VISIBLE
                    binding.recyclerviewMyAccounts.fadeIn(context)
                    binding.tvMyAccountCount.text = myAccountListLength.toString() + "개"
                    binding.ivArrowMini.setImageResource(R.drawable.ic_arrow_mini_down2)
//                    binding.recyclerviewMyAccounts.slideListDown(context)

                    true
                }
            }
        }

        setMyAccountList()

        with(binding){
            etInputAccount.setOnClickListener { findNavController().navigate(R.id.action_sendWhereFragment_to_sendWhereInputFragment) }
            toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        }
    }

    private fun setMyAccountList() {
        myCollectionAccountList.removeAll(myCollectionAccountList)

        val myAccountList = userViewModel.myInfo.value!!.account

        if (myAccountList.lastIndex > 0) {
            val myCollList = myAccountList.slice(1..myAccountList.lastIndex)

            myCollList.forEach { collection ->
                myCollectionAccountList.add(
                    MyAccount(
                        collection.name,
                        collection.number,
                        R.color.button
                    )
                )
            }
        }

        val myAccountsRecyclerView = binding.recyclerviewMyAccounts
        myAccountsRecyclerView.layoutManager = LinearLayoutManager(context)

        val myAccountAdapter = MyAccountAdapter(myCollectionAccountList, this)
        myAccountsRecyclerView.adapter = myAccountAdapter
    }

    private fun setRecentAccountList() {
        val recentAccountRecyclerView = binding.recyclerviewRecentAccount
        recentAccountRecyclerView.layoutManager = LinearLayoutManager(context)

        val recentAccountAdapter = RecentAccountAdapter(recentAccountList, this)
        recentAccountRecyclerView.adapter = recentAccountAdapter
    }

    override fun onMyAccountItemClick(position: Int) {
        val clickAccount = myCollectionAccountList[position]
        viewModel.setDepositAccountNumber(clickAccount.accountNumber)

        val action = SendWhereFragmentDirections.actionSendWhereFragmentToSendPointFragment()
        findNavController().navigate(action)
    }

    override fun onRecentAccountItemClick(position: Int) {
        val clickAccount = recentAccountList[position]
        viewModel.setDepositAccountNumber(clickAccount.accountNumber)

        val action = SendWhereFragmentDirections.actionSendWhereFragmentToSendPointFragment()
        findNavController().navigate(action)
    }

    private fun getRecentAccount() {
        viewModel.getRecentSpendList(userViewModel.myInfo.value!!.id)

        viewModel.recentSpendList.observe(viewLifecycleOwner) {
            recentAccountList = mutableListOf()

            viewModel.recentSpendList.value?.forEach { account ->
                Log.d(TAG, "start: List Add!!")
                recentAccountList.add(
                    RecentAccount(
                        account.name,
                        account.number,
                        R.drawable.ic_star
                    )
                )
            }
            setRecentAccountList()

            if (recentAccountList.isEmpty()) {
                binding.tvRecentAccount.visibility = View.GONE
            }
        }

    }
    companion object {
        const val TAG = "SendWhereFragment"
    }
}