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
            view.tvNoInternet.visibility = View.GONE
            view.btnRefresh.visibility = View.GONE
            view.tvAppNameSplash.visibility = View.VISIBLE
            val handler = Handler()
            handler.postDelayed({
                view.findNavController().navigate(R.id.action_ronanSplashFragment_to_quotesListFragment)
            }, 500)
        } else {
            view.tvNoInternet.visibility = View.VISIBLE
            view.btnRefresh.visibility = View.VISIBLE
            view.tvAppNameSplash.visibility = View.GONE
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnRefresh.setOnClickListener {
            activity?.recreate()
        }
    }
}