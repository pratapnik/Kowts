package com.ronan.kowts.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.ronan.kowts.R
import com.ronan.kowts.utils.isConnectionAvailable
import com.ronan.kowts.utils.makeViewGone
import com.ronan.kowts.utils.makeViewVisible
import kotlinx.android.synthetic.main.fragment_ronan_splash.*
import kotlinx.android.synthetic.main.fragment_ronan_splash.view.*
import kotlinx.android.synthetic.main.fragment_ronan_splash.view.btnRefresh

class RonanSplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ronan_splash, container, false)

        if (activity?.isConnectionAvailable()!!) {
            setLayoutWhenInternetAvailable(view)
            val handler = Handler()
            handler.postDelayed({
                view.findNavController().navigate(R.id.action_ronanSplashFragment_to_quotesListFragment)
            }, 500)
        } else {
            setLayoutWhenInternetNotAvailable(view)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnRefresh.setOnClickListener {
            activity?.recreate()
        }
    }

    private fun setLayoutWhenInternetAvailable(view: View){
        view.tvNoInternet.makeViewGone()
        view.btnRefresh.makeViewGone()
        view.tvAppNameSplash.makeViewVisible()
    }

    private fun setLayoutWhenInternetNotAvailable(view: View){
        view.tvNoInternet.makeViewVisible()
        view.btnRefresh.makeViewVisible()
        view.tvAppNameSplash.makeViewGone()
    }
}