package com.wonderfulsigma.flick.feature.qrcode

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.format.DateUtils.formatElapsedTime
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.github.alexzhirkevich.customqrgenerator.QrData
import com.github.alexzhirkevich.customqrgenerator.vector.QrCodeDrawable
import com.github.alexzhirkevich.customqrgenerator.vector.QrVectorOptions
import com.github.alexzhirkevich.customqrgenerator.vector.createQrVectorOptions
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorBallShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorColor
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorFrameShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorPixelShape
import com.sigma.data.network.dto.account.Account
import com.wonderfulsigma.flick.R
import com.wonderfulsigma.flick.feature.user.viewmodel.UserViewModel
import com.wonderfulsigma.flick.main.toDecimalFormat
import kotlin.properties.Delegates

class QRCode(
    private val userViewModel: UserViewModel,
    private val context: Context,
    private val viewLifecycleOwner: LifecycleOwner,
    owner: ViewModelStoreOwner,
    layoutInflater: LayoutInflater
) {
    // todo ; 결제 시 알람 오도록 만들기

    private val qrViewModel = ViewModelProvider(owner)[QRViewModel::class.java]

    val bottomSheetView: View = layoutInflater.inflate(R.layout.layout_bottom_sheet, null)

    private val qrCode: ImageView = bottomSheetView.findViewById(R.id.iv_qr_code)
    private val myCoin: TextView = bottomSheetView.findViewById(R.id.tv_payment_my_coin)
    private val btnGenerate: LinearLayout = bottomSheetView.findViewById(R.id.linear_re_generate)
    private val tvLeftTime: TextView = bottomSheetView.findViewById(R.id.tv_left_time)
    private val tvPleaseClickBtn: TextView = bottomSheetView.findViewById(R.id.tv_please_generate)
    private val linearLeftTime: LinearLayout = bottomSheetView.findViewById(R.id.linear_left_time)

    private lateinit var myData: QrData
    private lateinit var myOptions: QrVectorOptions
    private lateinit var qrCodeDrawable: Drawable

    private lateinit var myAccount: Account
    private var userId by Delegates.notNull<Long>()

    fun generateQRCode() {
        userViewModel.generateJwt(userId)
    }

    fun setQRCode() {
        setUserId()
        observeMyCoin()
        observeJwt()
        observeQRCode(viewLifecycleOwner)
        setReGenerate()
    }

    private fun setUserId() {
        myAccount = userViewModel.myInfo.value!!.account[0]
        userId = myAccount.id
    }

    private fun setReGenerate() {
        btnGenerate.setOnClickListener {
            userViewModel.generateJwt(userId)
        }
    }

    /** Observe Data */

    private fun observeMyCoin() {
        myCoin.text = myAccount.money.toDecimalFormat()
    }

    private fun observeJwt() {
        userViewModel.jwt.observe(viewLifecycleOwner) {
            myData = settingQRText()
            myOptions = settingOptions(context)

            qrCodeDrawable = QrCodeDrawable(myData, myOptions)

            tvPleaseClickBtn.visibility = View.INVISIBLE
            linearLeftTime.visibility = View.VISIBLE
            btnGenerate.isEnabled = false

            Log.d("QRCode", "myData: $myData") // todo . 로그가 여러번 뜨는 문제.. 생성 함수 안에 있어서?
            qrCode.setImageDrawable(qrCodeDrawable)

            qrViewModel.startTimer()
        }
    }

    private fun observeQRCode(lifecycleOwner: LifecycleOwner) {
        with(qrViewModel) {
            currentTime.observe(lifecycleOwner) { currentTime ->
                tvLeftTime.text = formatElapsedTime(currentTime).slice(3..4) + "초"
            }
            qrValidityFinish.observe(lifecycleOwner) { isFinished ->
                if (isFinished) {
                    tvPleaseClickBtn.visibility = View.VISIBLE
                    linearLeftTime.visibility = View.INVISIBLE
                    btnGenerate.isEnabled = true

                    qrCode.setImageDrawable(null)
                }
            }
        }
    }

    /** Design QRCode */

    private fun settingQRText(): QrData {
        val jwt = userViewModel.jwt.value.toString()
        return QrData.Text(jwt)
    }

    private fun settingOptions(
        context: Context,
    ): QrVectorOptions {
        val myOptions = createQrVectorOptions {
            padding = .10f

            colors {
                dark = QrVectorColor.Solid(
                    ContextCompat.getColor(context, R.color.light_black)
                ) // 가장 작은 점?
                ball = QrVectorColor.Solid(
                    ContextCompat.getColor(context, R.color.light_black)
                ) // 네모 안에 있는 사각형
                frame = QrVectorColor.Solid(
                    ContextCompat.getColor(context, R.color.light_black)
                ) // 큰 네모
            }
            shapes {
                darkPixel = QrVectorPixelShape
                    .RoundCorners(.0f)
                ball = QrVectorBallShape
                    .RoundCorners(.0f)
                frame = QrVectorFrameShape
                    .RoundCorners(.0f)
            }
        }
        return myOptions
    }
}


