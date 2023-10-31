package com.sigma.flick.feature.qrcode

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QRViewModel: ViewModel() {

    val currentTime = MutableLiveData<Long>()
    val qrValidityFinish = MutableLiveData<Boolean>()
    val userPoint = MutableLiveData<Int>()

    val QR_TIMER = 30000L
    val ONE_SECOND = 1000L
    val DONE = 0L

    private var timer: CountDownTimer

    init {
        userPoint.value = 0
        qrValidityFinish.value = false
        timer = object : CountDownTimer(QR_TIMER, ONE_SECOND) {
            override fun onTick(p0: Long) {
                currentTime.value = p0/ONE_SECOND
            }
            override fun onFinish() {
                qrValidityFinish.value = true
                currentTime.value = DONE
            }
        }
    }

    fun startTimer() {
        timer.start()
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }

}