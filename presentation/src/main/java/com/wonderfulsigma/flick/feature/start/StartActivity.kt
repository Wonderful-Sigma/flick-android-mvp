package com.wonderfulsigma.flick.feature.start

import android.content.Intent
import android.graphics.Color
import androidx.activity.viewModels
import com.sigma.data.network.dto.dauth.DauthLoginRequest
import com.wonderfulsigma.flick.R
import com.wonderfulsigma.flick.base.BaseActivity
import com.wonderfulsigma.flick.databinding.ActivityStartBinding
import com.wonderfulsigma.flick.main.MainActivity
import com.wonderfulsigma.flick.utils.clientId
import com.wonderfulsigma.flick.utils.clientSecret
import com.wonderfulsigma.flick.utils.redirectUrl
import com.wonderfulsigma.flick.utils.setStatusBarColorWhite
import dagger.hilt.android.AndroidEntryPoint
import kr.hs.dgsw.smartschool.dodamdodam.dauth.DAuth.settingDAuth

@AndroidEntryPoint
class StartActivity : BaseActivity<ActivityStartBinding, StartViewModel>(R.layout.activity_start) {

    override val viewModel: StartViewModel by viewModels()

    override fun start() {
        setStatusBarColorWhite(this, this)
        window.navigationBarColor = Color.WHITE

//        settingDAuth(clientId, clientSecret, redirectUrl)

        viewModel.autoLogin.observe(this) { autoLogin ->
            if (autoLogin) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        binding.btnDauth.setOnClickListener {
            viewModel.dauthLogin(
                DauthLoginRequest("jsw613", "jsw613!@#")
            )
//            viewModel.getCode(this)
        }
    }
}