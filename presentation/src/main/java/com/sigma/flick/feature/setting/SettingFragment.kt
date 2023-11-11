package com.sigma.flick.feature.setting

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentSettingBinding
import com.sigma.flick.feature.start.StartActivity
import com.sigma.flick.feature.user.viewmodel.UserViewModel
import com.sigma.flick.utils.HiltApplication

class SettingFragment: BaseFragment<FragmentSettingBinding, SettingViewModel>(R.layout.fragment_setting) {

    override val viewModel: SettingViewModel by viewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    override fun start() {
        userViewModel.myInfo.observe(this) { myInfo ->
            binding.tvMyName.text = myInfo?.name
            binding.tvMyAccountNumber.text = "대소코인 " + myInfo?.account!![0].number
        }

        with(binding) {
            btnBackArrow.setOnClickListener { findNavController().popBackStack() }
            btnRule.setOnClickListener {
                Toast.makeText(requireContext(), "약관 및 개인정보 처리 동의", Toast.LENGTH_SHORT).show()
                // todo :
            }
            btnVersion.setOnClickListener {
                Toast.makeText(requireContext(), "앱 버전", Toast.LENGTH_SHORT).show()
                // todo :
            }
            btnLogout.setOnClickListener {
                Toast.makeText(requireContext(), "로그아웃 되었어요", Toast.LENGTH_SHORT).show()
                HiltApplication.prefs.deleteToken()
                startActivity(Intent(activity, StartActivity::class.java))
            }
        }
    }

}