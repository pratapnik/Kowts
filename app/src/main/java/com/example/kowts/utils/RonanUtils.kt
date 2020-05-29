package com.example.kowts.utils

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(snackBarText: String){
    val snackBar = Snackbar.make(this, snackBarText, Snackbar.LENGTH_LONG)
    snackBar.show()
}

fun Context.isConnectionAvailable(): Boolean{

    var connected = false
    try {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nInfo = cm.activeNetworkInfo
        connected = nInfo != null && nInfo.isAvailable && nInfo.isConnected
        return connected
    } catch (e: Exception) {
        Log.e("Connectivity Exception", e.message)
    }
    return connected

}