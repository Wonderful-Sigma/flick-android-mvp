package com.sigma.flick.feature.collection.screen.start

import androidx.fragment.app.activityViewModels
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentCollectionPeopleBinding
import com.sigma.flick.feature.collection.viewmodel.CollectionViewModel

class CollectionPeopleFragment: BaseFragment<FragmentCollectionPeopleBinding, CollectionViewModel>(R.layout.fragment_collection_people) {
    override val viewModel: CollectionViewModel by activityViewModels()
    override fun start() {

    }
}