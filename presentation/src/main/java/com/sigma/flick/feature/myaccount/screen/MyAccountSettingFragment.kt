package com.sigma.flick.feature.myaccount.screen

import android.content.ClipData
import android.content.ClipboardManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentMyAccountSettingBinding
import com.sigma.flick.feature.myaccount.viewmodel.BankbookRecordsViewModel
import com.sigma.flick.feature.user.viewmodel.UserViewModel

class MyAccountSettingFragment: BaseFragment<FragmentMyAccountSettingBinding, BankbookRecordsViewModel>(R.layout.fragment_my_account_setting) {

    override val viewModel: BankbookRecordsViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    override fun start() {
        binding.btnBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        val userAccountNumber = userViewModel.myInfo.value!!.account[0].number
        binding.tvMyAccountNumber.text = userAccountNumber

        binding.btnCopy.setOnClickListener {
            Toast.makeText(requireContext(), "복사", Toast.LENGTH_SHORT).show()
//            val clipboardManager: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
//            clipboardManager.copyToClipboard(userAccountNumber)
            //copyString(userAccountNumber) //todo : java.lang.ClassCastException: java.lang.String cannot be cast to android.content.ClipboardManager
        }

        binding.linearMyAccountIconName.setOnClickListener {
            Toast.makeText(requireContext(), "아이콘, 별명", Toast.LENGTH_SHORT).show()
        }

        binding.linearAccountInfo.setOnClickListener {
            Toast.makeText(requireContext(), "계좌 정보", Toast.LENGTH_SHORT).show()
        }
    }

    private fun ClipboardManager.copyToClipboard(text: String) {
        val clipData = ClipData.newPlainText("label", text)
        setPrimaryClip(clipData)
    }
    private fun copyString(text: String) {
        val clipboard: ClipboardManager = getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(requireContext(), "복사되었어요", Toast.LENGTH_SHORT).show()
    }

}