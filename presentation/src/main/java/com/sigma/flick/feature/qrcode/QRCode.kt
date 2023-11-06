package com.sigma.flick.feature.qrcode

import android.content.Context
import android.graphics.drawable.Drawable
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
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogoPadding
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogoShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorPixelShape
import com.sigma.flick.R
import com.sigma.flick.feature.user.viewmodel.UserViewModel
import com.sigma.main.model.account.Account

class QRCode(
    private val userViewModel: UserViewModel,
    private val context: Context,
    private val viewLifecycleOwner: LifecycleOwner,
    owner: ViewModelStoreOwner,
    layoutInflater: LayoutInflater
) {
    // todo ; 결제 시 알람 오도록 만들기

    private val qrViewModel = ViewModelProvider(owner)[QRViewModel::class.java]

    // viewModel을 MainActivity에서 만들고, 갖다 쓰면 안되나?
    // todo ; 하나의 인스턴스로 만들기

    val bottomSheetView: View = layoutInflater.inflate(R.layout.layout_bottom_sheet, null)

    private val qrCode: ImageView = bottomSheetView.findViewById(R.id.iv_qr_code)
    private val myCoin: TextView = bottomSheetView.findViewById(R.id.tv_payment_my_coin)
    private val btnGenerate: LinearLayout = bottomSheetView.findViewById(R.id.linear_re_generate)
    private val tvLeftTime: TextView = bottomSheetView.findViewById(R.id.tv_left_time)
    private val tvPleaseClickBtn: TextView = bottomSheetView.findViewById(R.id.tv_please_generate)
    private val linearLeftTime: LinearLayout = bottomSheetView.findViewById(R.id.linear_left_time)

    private lateinit var myAccount: Account

    private lateinit var myData: QrData
    private lateinit var myOptions: QrVectorOptions
    private lateinit var qrCodeDrawable: Drawable

    fun observeMyCoin() {
        myCoin.text = myAccount.money.toString() + "코인"
    }

    fun generateQRCode() {
        myAccount = userViewModel.myInfo.value!!.account[0]
        val userId = myAccount.id

        userViewModel.generateJwt(userId)

        // 다시 생성
        btnGenerate.setOnClickListener {
            userViewModel.generateJwt(userId)

            tvPleaseClickBtn.visibility = View.INVISIBLE
            linearLeftTime.visibility = View.VISIBLE
            btnGenerate.isEnabled = false

            qrCode.setImageDrawable(qrCodeDrawable)

            qrViewModel.startTimer()
        }

        userViewModel.jwt.observe(viewLifecycleOwner) {
            myData = settingQRText()
            myOptions = settingOptions(context)

            qrCodeDrawable = QrCodeDrawable(myData, myOptions)

            tvPleaseClickBtn.visibility = View.INVISIBLE
            linearLeftTime.visibility = View.VISIBLE
            btnGenerate.isEnabled = false

            Log.d("QRCode", "myData: $myData") // todo . 로그가 여러번 뜨는 문제..
            qrCode.setImageDrawable(qrCodeDrawable)

            qrViewModel.startTimer()
        }
    }


    fun observeQRCode(lifecycleOwner: LifecycleOwner) {
        with(qrViewModel) {
            currentTime.observe(lifecycleOwner) { currentTime ->
                tvLeftTime.text = android.text.format.DateUtils.formatElapsedTime(currentTime).slice(3..4) + "초"
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

    private fun settingQRText(): QrData {
        val jwt = userViewModel.jwt.value.toString()
        return QrData.Text(jwt)
    }
}

fun settingOptions(
    context: Context,
): QrVectorOptions {
    val myOptions = createQrVectorOptions { // todo : qr code size 키우는 법 없나...
        padding = .10f

        logo {
            drawable = ContextCompat
                .getDrawable(context, R.drawable.ic_flick_gray_png) // todo : 이미지 바꾸기
            size = .25f
            padding = QrVectorLogoPadding.Natural(.2f)
            shape = QrVectorLogoShape
                .Circle
        }
        colors {
            dark = QrVectorColor.Solid(
                ContextCompat.getColor(context, R.color.sub_title)
            ) // 가장 작은 점?
            ball = QrVectorColor.Solid(
                ContextCompat.getColor(context, R.color.sub_title)
            ) // 네모 안에 있는 사각형
            frame = QrVectorColor.Solid(
                ContextCompat.getColor(context, R.color.sub_title)
            ) // 큰 네모
        }
        shapes {
            darkPixel = QrVectorPixelShape
                .RoundCorners(.5f)
            ball = QrVectorBallShape
                .RoundCorners(.35f)
            frame = QrVectorFrameShape
                .RoundCorners(.35f)
        }
    }
    return myOptions
}
