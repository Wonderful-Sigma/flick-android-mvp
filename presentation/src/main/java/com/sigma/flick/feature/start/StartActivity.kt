package com.sigma.flick.feature.start

import android.content.Intent
import android.graphics.Color
import androidx.activity.viewModels
import com.sigma.flick.R
import com.sigma.flick.base.BaseActivity
import com.sigma.flick.databinding.ActivityStartBinding
import com.sigma.flick.main.MainActivity
import com.sigma.flick.utils.clientId
import com.sigma.flick.utils.clientSecret
import com.sigma.flick.utils.redirectUrl
import com.sigma.flick.utils.setStatusBarColorWhite
import dagger.hilt.android.AndroidEntryPoint
import kr.hs.dgsw.smartschool.dodamdodam.dauth.DAuth.settingDAuth

@AndroidEntryPoint
class StartActivity : BaseActivity<ActivityStartBinding, StartViewModel>(R.layout.activity_start) {

    override val viewModel: StartViewModel by viewModels()

    override fun start() {
        setStatusBarColorWhite(this, this)
        window.navigationBarColor = Color.WHITE

        settingDAuth(clientId, clientSecret, redirectUrl)

        viewModel.autoLogin.observe(this) { autoLogin ->
            if (autoLogin) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        binding.btnDauth.setOnClickListener {
            viewModel.getCode(this)
        }
    }
}