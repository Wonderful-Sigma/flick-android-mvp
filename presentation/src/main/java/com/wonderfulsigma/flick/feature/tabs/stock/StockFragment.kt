package com.wonderfulsigma.flick.feature.tabs.stock

import androidx.fragment.app.viewModels
import com.wonderfulsigma.flick.R
import com.wonderfulsigma.flick.base.BaseFragment
import com.wonderfulsigma.flick.databinding.FragmentStockBinding

class StockFragment: BaseFragment<FragmentStockBinding, StockViewModel>(R.layout.fragment_stock) {

    override val viewModel: StockViewModel by viewModels()

    override fun start() {

    }

}