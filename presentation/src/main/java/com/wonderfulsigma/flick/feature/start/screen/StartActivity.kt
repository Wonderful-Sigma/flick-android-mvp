package com.wonderfulsigma.flick.feature.start.screen

import android.content.Intent
import android.graphics.Color
import androidx.activity.viewModels
import com.wonderfulsigma.flick.R
import com.wonderfulsigma.flick.base.BaseActivity
import com.wonderfulsigma.flick.databinding.ActivityStartBinding
import com.wonderfulsigma.flick.feature.start.StartViewModel
import com.wonderfulsigma.flick.main.MainActivity
import com.wonderfulsigma.flick.utils.setStatusBarColorWhite
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StartActivity : BaseActivity<ActivityStartBinding, StartViewModel>(R.layout.activity_start) {

    override val viewModel: StartViewModel by viewModels()

    override fun start() {
        setStatusBarColorWhite(this, this)
        window.navigationBarColor = Color.WHITE

        viewModel.autoLogin.observe(this) { autoLogin ->
            if (autoLogin) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        binding.btnDauth.setOnClickListener {

        }
    }

}