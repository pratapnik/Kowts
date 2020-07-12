package com.ronan.kowts

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.ronan.kowts.actions.Constants
import com.ronan.kowts.utils.isConnectionAvailable
import com.ronan.kowts.utils.makeViewGone
import com.ronan.kowts.utils.makeViewVisible
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        enableDarkMode()
        if (isConnectionAvailable()) {
            setLayoutWhenInternetAvailable()
            val handler = Handler()
            handler.postDelayed({
                val homeIntent = Intent(this, HomeActivity::class.java)
                startActivity(homeIntent)
                finish()
            }, 500)
        } else {
            setLayoutWhenInternetNotAvailable()
        }

        btnRefresh.setOnClickListener {
            recreate()
        }
    }

    private fun setLayoutWhenInternetAvailable() {
        tvNoInternet.makeViewGone()
        btnRefresh.makeViewGone()
        tvAppNameSplash.makeViewVisible()
    }

    private fun setLayoutWhenInternetNotAvailable() {
        tvNoInternet.makeViewVisible()
        btnRefresh.makeViewVisible()
        tvAppNameSplash.makeViewGone()
    }

    private fun enableDarkMode() {
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this)
        if (sharedPreferences.getBoolean(Constants.SettingsStrings.DARK_MODE, false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}