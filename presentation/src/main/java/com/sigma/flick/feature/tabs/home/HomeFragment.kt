package com.sigma.flick.feature.tabs.home

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sigma.data.network.dto.account.Account
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentHomeBinding
import com.sigma.flick.feature.tabs.home.viewmodel.HomeViewModel
import com.sigma.flick.feature.user.viewmodel.UserViewModel
import com.sigma.flick.feature.qrcode.QRCode
import com.sigma.flick.main.toDecimalFormat
import com.sigma.flick.utils.setStatusBarColorBackground
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModels()
    val userViewModel: UserViewModel by activityViewModels()

    private lateinit var context: Context

    override fun start() {
        context = requireContext()
        setStatusBarColorBackground(requireActivity(), context)

        observeMyInfo()

        /** QR Code */
        showQRCode(context)

        //** navigation */
        setNavigation()

        binding.home.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                userViewModel.getUserInfo()
                binding.home.isRefreshing = false
            }, 1000)
        }
    }

    private fun observeMyInfo() {
        userViewModel.myInfo.observe(viewLifecycleOwner) { myInfo ->
            val myAccount = myInfo?.account!![0]
            binding.tvMyAccount.text = myAccount.name
            binding.tvMyCoin.text = myAccount.money.toDecimalFormat()
        }
        val myAccount: Account? = userViewModel.myInfo.value?.account?.get(0)
        if (myAccount != null) {
            binding.tvMyAccount.text = myAccount.name
            binding.tvMyCoin.text = myAccount.money.toDecimalFormat()
        }
    }

    private fun setNavigation() {
        with(binding) {
            btnSend.setOnClickListener { findNavController().navigate(R.id.to_sendWhereFragment) }
            linearMyAccount.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_fragmentBankbookRecords) }
            bankbookButton.setOnClickListener { findNavController().navigate(R.id.to_accountDetailFragment) }
            alarmButton.setOnClickListener {
                Toast.makeText(context, "아직은 알림 기능이 없어요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showQRCode(context: Context) {
        val qrCodeClass =
            QRCode(userViewModel, context, viewLifecycleOwner, this@HomeFragment, layoutInflater)

        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(qrCodeClass.bottomSheetView)

        binding.paymentButton.setOnClickListener {
            qrCodeClass.setQRCode()
            bottomSheetDialog.show()
            qrCodeClass.generateQRCode()
        }
    }

    companion object {
        const val TAG = "HomeFragment"
    }

}