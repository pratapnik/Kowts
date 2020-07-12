package com.example.presence.fragments

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import com.ronan.kowts.R
import com.ronan.kowts.actions.Constants

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var sharedPreferenceChangeListener: SharedPreferences.OnSharedPreferenceChangeListener

    var darkModeListener: DarkModeListener?=null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        addPreferencesFromResource(R.xml.root_preferences)

        sharedPreferenceChangeListener = OnSharedPreferenceChangeListener { prefs, key ->
            Log.d("nikhil", "onCreatePreferences: "+key)
            if (key == Constants.SettingsStrings.DARK_MODE &&
                prefs.getBoolean(Constants.SettingsStrings.DARK_MODE, false)
            ) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                darkModeListener?.onDarkModeSwitch(true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                darkModeListener?.onDarkModeSwitch(true)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(
            sharedPreferenceChangeListener
        )
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(
            sharedPreferenceChangeListener
        )
    }

    interface DarkModeListener{
        fun onDarkModeSwitch(isSwitched: Boolean)
    }

}
