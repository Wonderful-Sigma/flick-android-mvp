package com.sigma.flick.feature.stock

import androidx.fragment.app.viewModels
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentStockBinding

class StockFragment: BaseFragment<FragmentStockBinding, StockViewModel>(R.layout.fragment_stock) {

    override val viewModel: StockViewModel by viewModels()

    override fun start() {

    }

}