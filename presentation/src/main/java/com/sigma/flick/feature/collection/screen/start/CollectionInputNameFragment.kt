package com.sigma.flick.feature.collection.screen.start

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentCollectionInputNameBinding
import com.sigma.flick.feature.collection.viewmodel.CollectionViewModel
import com.sigma.flick.feature.user.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CollectionInputNameFragment :
    BaseFragment<FragmentCollectionInputNameBinding, CollectionViewModel>(R.layout.fragment_collection_input_name) {

    override val viewModel: CollectionViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    override fun start() {
        val bottomNavigationBar = activity?.findViewById<BottomNavigationView>(R.id.bnv)
        bottomNavigationBar?.visibility = View.GONE

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnNext.setOnClickListener {
            val memberId = userViewModel.myInfo.value?.id ?: ""
            val etCollection = binding.etCollection.text.toString()

            if (etCollection.isNotEmpty()) {
                viewModel.generateCollectionAccount(memberId, etCollection)
                viewModel.accountData.observe(this){
                    Log.d("중간",it.toString())
                    val accountNumber = it.number
                    Log.d("이게 왜",accountNumber)
                    val bundle = Bundle()
                    bundle.putString("accountNumber",accountNumber)
                    findNavController().navigate(R.id.to_collectionCompletionFragment, bundle)
                }
            }

        }

    }

}