package com.ronan.kowts

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.presence.fragments.SettingsFragment
import com.ronan.kowts.utils.showToastAtCenter
import kotlinx.android.synthetic.main.activity_home.*
import nl.joery.animatedbottombar.AnimatedBottomBar

class HomeActivity : AppCompatActivity(), SettingsFragment.DarkModeListener {

    private lateinit var navController: NavController
    private lateinit var settingsFragment: SettingsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navController = findNavController(R.id.fragment_container)
        Navigation.setViewNavController(bottomBar, navController)

        settingsFragment = SettingsFragment()
        settingsFragment.darkModeListener = this

        bottomBar.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int, lastTab: AnimatedBottomBar.Tab?, newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                selectTabs(newIndex)
            }

            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {
                selectTabs(index)
            }
        })
    }

    private fun selectTabs(tabIndex: Int) {
        when (tabIndex) {
            0 -> navController.navigate(R.id.quotesListFragment)
            1 -> navController.navigate(R.id.categoriesFragment)
            2 -> navController.navigate(R.id.settingsFragment2)
        }
    }

    override fun onDarkModeSwitch(isSwitched: Boolean) {
        Log.d("nikhil", "onDarkModeSwitch: $isSwitched")
        if(isSwitched)
            selectTabs(0)
    }
}