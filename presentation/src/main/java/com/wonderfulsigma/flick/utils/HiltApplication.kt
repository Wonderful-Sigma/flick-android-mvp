package com.wonderfulsigma.flick.utils

import android.app.Application
import com.sigma.data.utils.PreferenceManager
import dagger.hilt.android.HiltAndroidApp
@HiltAndroidApp
class HiltApplication : Application() {

    companion object {
        lateinit var prefs: PreferenceManager
    }

    override fun onCreate() {
        prefs = PreferenceManager(applicationContext)
        super.onCreate()
    }
}