package com.sigma.flick.feature.bankbook_setting

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.R
import com.sigma.flick.databinding.FragmentBankbookSettingBinding
import com.sigma.flick.feature.bankbook_setting.viewmodel.BankbookSettingViewModel


class BankbookSettingFragment :
    BaseFragment<FragmentBankbookSettingBinding, BankbookSettingViewModel>(R.layout.fragment_bankbook_setting) {

    override val viewModel: BankbookSettingViewModel by viewModels()

    override fun start() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        with(binding) {
            btnAccountProfileSetting.setOnClickListener { }
            btnAccountInformation.setOnClickListener { }
        }
    }
}