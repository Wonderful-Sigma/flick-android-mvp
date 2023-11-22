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
import com.sigma.flick.feature.send.recyclerview.recent.RecentAccount
import com.sigma.flick.feature.send.recyclerview.recent.RecentAccountAdapter
import com.sigma.flick.feature.send.viewmodel.SendViewModel
import com.sigma.flick.feature.user.viewmodel.UserViewModel
import com.sigma.flick.utils.setDeleteBottomNav
import com.sigma.flick.utils.setPopBackStack
import com.sigma.flick.utils.setStatusBarColorWhite
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SendWhereFragment : BaseFragment<FragmentSendWhereBinding, SendViewModel>(R.layout.fragment_send_where), RecentAccountAdapter.OnRecentAccountItemClickListener {

    override val viewModel: SendViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    private lateinit var context: Context

    private var recentAccountList: MutableList<RecentAccount> = mutableListOf()

    override fun start() {
        setDeleteBottomNav(activity)
        binding.toolbar.setPopBackStack()
        setStatusBarColorWhite(requireActivity(), requireContext())

        context = requireContext()

        /** Recent Spend List */

        getRecentAccount()

        with(binding){
            etInputAccount.setOnClickListener { findNavController().navigate(R.id.action_sendWhereFragment_to_sendWhereInputFragment) }
            toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        }
    }

    private fun setRecentAccountList() {
        val recentAccountRecyclerView = binding.recyclerviewRecentAccount
        recentAccountRecyclerView.layoutManager = LinearLayoutManager(context)

        val recentAccountAdapter = RecentAccountAdapter(recentAccountList, this)
        recentAccountRecyclerView.adapter = recentAccountAdapter
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