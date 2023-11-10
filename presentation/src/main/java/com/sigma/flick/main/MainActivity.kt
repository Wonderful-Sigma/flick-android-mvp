package com.sigma.flick.main

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.getString
import dagger.hilt.android.AndroidEntryPoint
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.messaging.FirebaseMessaging
import com.sigma.flick.R
import com.sigma.flick.R.id.fragment_container
import com.sigma.flick.databinding.ActivityMainBinding
import com.sigma.flick.feature.qrcode.QRCode
import com.sigma.flick.feature.user.viewmodel.UserViewModel
import com.sigma.flick.utils.setStatusBarColorBackground
import com.sigma.flick.utils.setStatusBarColorWhite

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val userViewModel: UserViewModel by viewModels()

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

//        getFCMToken()  // TODO : 나중에 추가 예정

        userViewModel.getUserInfo() // TODO : 다 받아오고 뷰 그리기

        val navHostFragment = supportFragmentManager.findFragmentById(fragment_container) as NavHostFragment
        navController = navHostFragment.findNavController()

        binding.bnv.setupWithNavController(navController)

        userViewModel.myInfo.observe(this) {
            setQRCode()
            setBottomNavigation()
        }
    }

    private fun getFCMToken(): String?{
        var token: String? = null
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            token = task.result
            Log.d(TAG, "FCM Token is ${token}")
            Toast.makeText(this, "success to get token", Toast.LENGTH_SHORT).show()
        })

        return token
    }

    private fun setQRCode() {
        /** QR Code */
        val qrCodeClass = QRCode(userViewModel, this, this, this, layoutInflater)

        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(qrCodeClass.bottomSheetView)

        binding.bnv.menu.findItem(R.id.paymentFragment).setOnMenuItemClickListener {
            qrCodeClass.setQRCode()
            bottomSheetDialog.show()
            qrCodeClass.generateQRCode()
            return@setOnMenuItemClickListener false
        }
    }

    private fun setBottomNavigation() {
        /** Bottom Navigation */
        navController.addOnDestinationChangedListener { _, destination, _ ->
            /** Background Color & Status Bar Color */
            if (destination.id == R.id.homeFragment) {
                binding.root.setBackgroundColor(resources.getColor(R.color.activity_background))
                setStatusBarColorBackground(this, this)
            } else {
                binding.root.setBackgroundColor(Color.WHITE)
                setStatusBarColorWhite(this, this)
            }

            /** Bottom Nav */
            if (destination.id == R.id.homeFragment || destination.id == R.id.allFragment || destination.id == R.id.stockFragment ||
                destination.id == R.id.paymentFragment || destination.id == R.id.eventFragment) {
                Log.d("상태", "보이기")
                binding.bnv.visibility = View.VISIBLE
            } else {
                Log.d("상태", "숨기기")
                binding.bnv.visibility = View.GONE
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}