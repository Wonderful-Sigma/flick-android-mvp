package com.wonderfulsigma.flick.feature.start

import android.content.Intent
import android.graphics.Color
import androidx.activity.viewModels
import com.sigma.data.network.dto.dauth.DauthLoginRequest
import com.wonderfulsigma.flick.R
import com.wonderfulsigma.flick.base.BaseActivity
import com.wonderfulsigma.flick.databinding.ActivityStartBinding
import com.wonderfulsigma.flick.main.MainActivity
import com.wonderfulsigma.flick.utils.setStatusBarColorWhite
import dagger.hilt.android.AndroidEntryPoint
import java.math.BigInteger
import java.security.MessageDigest


@AndroidEntryPoint
class StartActivity : BaseActivity<ActivityStartBinding, StartViewModel>(R.layout.activity_start) {

    override val viewModel: StartViewModel by viewModels()

    override fun start() {
        setStatusBarColorWhite(this, this)
        window.navigationBarColor = Color.WHITE

//        settingDAuth(CLIENT_ID, CLIENT_SECRET, REDIRECT_URL)

        viewModel.autoLogin.observe(this) { autoLogin ->
            if (autoLogin) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        val id = "jjosumin"
        val pw = getHash("jjosumin123")

        binding.btnDauth.setOnClickListener {
            viewModel.dauthLogin(
                DauthLoginRequest(id, pw)
            )
//            viewModel.getCode(this)
        }
    }

    private fun getHash(str: String): String {
        val md = MessageDigest.getInstance("SHA-512")
        md.update(str.toByteArray())
        return String.format("%0128x", BigInteger(1, md.digest()))
    }
    
}