package com.sigma.flick.feature.collection.screen.start

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentCollectionCompletionBinding
import com.sigma.flick.feature.collection.viewmodel.CollectionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionCompletionFragment :
    BaseFragment<FragmentCollectionCompletionBinding, CollectionViewModel>(R.layout.fragment_collection_completion) {

    override val viewModel: CollectionViewModel by activityViewModels()

    override fun start() {
        val bottomNavigationBar = activity?.findViewById<BottomNavigationView>(R.id.bnv)
        bottomNavigationBar?.visibility = View.GONE

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnOkay.setOnClickListener {
            val accountNumber = arguments?.getString("accountNumber")
            val bundle = Bundle()
            bundle.putString("accountNumber",accountNumber)
            findNavController().navigate(R.id.to_collectionDetailFragment,bundle)
        }

    }

}