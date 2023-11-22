package com.sigma.flick.feature.start

import android.content.Intent
import android.graphics.Color
import android.view.View
import androidx.activity.viewModels
import com.sigma.flick.R
import com.sigma.flick.base.BaseActivity
import com.sigma.flick.databinding.ActivityStartBinding
import com.sigma.flick.main.MainActivity
import com.sigma.flick.utils.HiltApplication
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
        window.navigationBarColor = Color.WHITE
        setStatusBarColorWhite(this, this)

        settingDAuth(clientId, clientSecret, redirectUrl)

        viewModel.autoLogin.observe(this) { autoLogin ->
            if (autoLogin) {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                finish()
            }
        }

        binding.btnDauth.setOnClickListener {
            viewModel.getCode(this)
//            viewModel.getNewAccessToken("eyJKV1QiOiJSRUZSRVNIX1RPS0VOIiwiYWxnIjoiSFM1MTIifQ.eyJzdWIiOiI3NzJhZDBkMy0xYTRhLTRhNTUtYmE1MC1jNmI3ZWNlZDFmYjAiLCJuYW1lIjoi7KGw7Iq57JmEIiwicnVsZSI6IlNUVURFTlQiLCJpYXQiOjE2OTk5NjU3MjYsImV4cCI6MTcwMjU1NzcyNn0.K3Ep_llIaGrh_WHbZ8woVmairvTllWA1WggmSPYmG753yaeXrHXvistTKkzv6Sgzgx_Ag1WMyk1vMjCRUzz-BA")
        }

//        HiltApplication.prefs.deleteAccessToken()
//        HiltApplication.prefs.accessToken = "eyJKV1QiOiJBQ0NFU1NfVE9LRU4iLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3NzJhZDBkMy0xYTRhLTRhNTUtYmE1MC1jNmI3ZWNlZDFmYjAiLCJuYW1lIjoi7KGw7Iq57JmEIiwicnVsZSI6IlNUVURFTlQiLCJpYXQiOjE2OTk5NjU3MjYsImV4cCI6MTY5OTk2NTczNn0.mgxPjBM64w7C_mb4I8W_Jx8us365i5-N2HKMrKEgmAaw4MH1_PJ2rkm3YYEGdF-LESrAGwcZodO5t-3PaRrviA"
//        Toast.makeText(this, "액세스 토큰을 저장했습니다.", Toast.LENGTH_SHORT).show()
    }
}