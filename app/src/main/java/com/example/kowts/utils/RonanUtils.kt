package com.example.kowts.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
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

fun Activity.copyText(text:String){
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("joke", text)
    clipboard.setPrimaryClip(clip)
}

fun Activity.sendText(text: String){
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.putExtra(Intent.EXTRA_TEXT, text)
    shareIntent.type = "text/plain"
    val sendIntent = Intent.createChooser(shareIntent, "Send Joke")
    startActivity(sendIntent)
}