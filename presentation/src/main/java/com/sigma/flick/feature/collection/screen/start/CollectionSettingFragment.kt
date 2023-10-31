package com.sigma.flick.feature.collection.screen.start

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentCollectionSettingBinding
import com.sigma.flick.feature.collection.viewmodel.CollectionViewModel

class CollectionSettingFragment :
    BaseFragment<FragmentCollectionSettingBinding, CollectionViewModel>(
        R.layout.fragment_collection_setting
    ) {
    override val viewModel: CollectionViewModel by activityViewModels()

    override fun start() {
        viewModel.accountData.observe(this) {
            with(binding) {
                backButton.setOnClickListener { findNavController().popBackStack() }
                textBankbookName.text
                bankbookNumber.text = "계좌번호 * " + ""
            }
        }
        with(binding){
            btnMember.setOnClickListener {
                findNavController().navigate(R.id.to_collectionPeopleFragment)
            }
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }

    }
}