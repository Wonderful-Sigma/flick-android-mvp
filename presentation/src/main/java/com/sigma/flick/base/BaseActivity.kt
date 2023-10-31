package com.sigma.flick.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes private val layoutRes: Int
) : AppCompatActivity() {

    protected lateinit var binding: B
    protected lateinit var mViewModel: VM
    protected abstract val viewModel: VM

    protected open fun preLoad() {}
    protected abstract fun start()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preLoad()
        prepareDataBinding()
        start()
    }

    private fun prepareDataBinding() {
        binding = DataBindingUtil.setContentView(this, layoutRes)
        mViewModel = if (::mViewModel.isInitialized) mViewModel else viewModel
//        binding.setVariable(BR.vm, viewModel)
        binding.lifecycleOwner = this
        binding.executePendingBindings()

    }

    override fun onDestroy() {
        super.onDestroy()
        if (::binding.isInitialized) binding.unbind()
    }
}