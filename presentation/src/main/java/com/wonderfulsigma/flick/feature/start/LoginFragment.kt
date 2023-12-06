package com.wonderfulsigma.flick.feature.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.wonderfulsigma.flick.R
import com.wonderfulsigma.flick.base.BaseFragment
import com.wonderfulsigma.flick.databinding.FragmentLoginBinding

class LoginFragment: BaseFragment<FragmentLoginBinding, StartViewModel>(R.layout.fragment_login) {
    override val viewModel: StartViewModel by viewModels()

    override fun start() {
        
    }

}