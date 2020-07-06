package com.ronan.kowts

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.ronan.kowts.utils.isConnectionAvailable
import com.ronan.kowts.utils.makeViewGone
import com.ronan.kowts.utils.makeViewVisible
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

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
}