package com.ronan.kowts.fragments

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ronan.kowts.R
import com.ronan.kowts.actions.RonanQuoteBottomSheetAction
import com.ronan.kowts.adapters.RonanQuotesListAdapter
import com.ronan.kowts.data.QuotesDataModel
import com.ronan.kowts.utils.*
import com.ronan.kowts.viewmodels.RonanListViewModel
import com.ronan.kowts.widgets.RonanQuoteMenuBottomSheet
import kotlinx.android.synthetic.main.ronan_quotes_list_fragment.*
import kotlinx.android.synthetic.main.ronan_quotes_list_fragment.view.*


class RonanQuotesListFragment : Fragment(), RonanQuotesListAdapter.QuoteClickListener,
    RonanQuoteMenuBottomSheet.ActionListener {

    private lateinit var ronanListViewModel: RonanListViewModel
    private val quotesListAdapter = RonanQuotesListAdapter(arrayListOf())
    private lateinit var ronanQuoteMenuBottomSheet: RonanQuoteMenuBottomSheet

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.ronan_quotes_list_fragment, container, false)

        checkInternet(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ronanListViewModel = ViewModelProviders.of(this).get(RonanListViewModel::class.java)
        ronanListViewModel.refresh()

        ronanQuoteMenuBottomSheet = RonanQuoteMenuBottomSheet()
        ronanQuoteMenuBottomSheet.actionListener = this

        rvQuotesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = quotesListAdapter
        }

        fabGoToTop.setOnClickListener {
            rvQuotesList.smoothScrollToPosition(0)
        }

        ivRefresh.setOnClickListener {
            rvQuotesList.visibility = View.GONE
            tvErrorOccured.visibility = View.GONE
            pbLoadData.visibility = View.VISIBLE
            ronanListViewModel.refresh()
        }

        fabShuffle.setOnClickListener {
            rvQuotesList.visibility = View.GONE
            tvErrorOccured.visibility = View.GONE
            pbLoadData.visibility = View.VISIBLE
            ronanListViewModel.refreshShuffle()
        }

        btnRefreshList.setOnClickListener {
            activity?.recreate()
        }

        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action === KeyEvent.ACTION_UP) {
                fragmentManager?.popBackStack(
                    "null",
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                activity?.finish()
                return@OnKeyListener true
            }
            false
        })

        quotesListAdapter.quoteClickListener = this

        observeViewModel()
    }

    private fun observeViewModel() {
        ronanListViewModel.quotes.observe(viewLifecycleOwner, Observer { quotes ->
            quotes?.let {
                rvQuotesList.visibility = View.VISIBLE
                Log.d("nikhil", "observeViewModel: "+quotes[0].quoteText)
                quotesListAdapter.updateQuotesList(quotes as ArrayList<QuotesDataModel>)
            }
        })

        ronanListViewModel.quotesError.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("nikhil", "error: $it")
                tvErrorOccured.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        ronanListViewModel.loading.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("nikhil", "error: $it")
                pbLoadData.visibility = if (it) View.VISIBLE else View.GONE

                if (it) {
                    rvQuotesList.visibility = View.GONE
                    tvErrorOccured.visibility = View.GONE
                }
            }
        })
    }

    private fun checkInternet(view: View){
        if(activity?.isConnectionAvailable()!!){
            setListLayoutWithInternet(view)
        }
        else{
            setListLayoutNoInternet(view)
        }
    }

    private fun setListLayoutWithInternet(view: View){
        view.tvNoInternetList.visibility = View.GONE
        view.btnRefreshList.visibility = View.GONE
        view.rvQuotesList.visibility = View.VISIBLE
    }

    private fun setListLayoutNoInternet(view: View){
        view.tvNoInternetList.visibility = View.VISIBLE
        view.btnRefreshList.visibility = View.VISIBLE
        view.tvErrorOccured.visibility = View.GONE
        view.pbLoadData.visibility = View.GONE
        view.rvQuotesList.visibility = View.GONE
    }

    override fun onActionListener(action: RonanQuoteBottomSheetAction, fullQuoteText: String) {
        when (action) {
            RonanQuoteBottomSheetAction.COPY_QUOTE -> {
                activity?.copyText(fullQuoteText)
                layoutQuotesList.showSnackBar(resources.getString(R.string.label_quote_copied))
            }
            RonanQuoteBottomSheetAction.SHARE_QUOTE -> {
                activity?.sendText(fullQuoteText)
            }
        }
    }

    override fun onQuoteClickListener(quoteText: String, quoteAuthor: String) {
        ronanQuoteMenuBottomSheet.show(
            childFragmentManager,
            resources.getString(R.string.label_open_quote_menu)
        )
        ronanQuoteMenuBottomSheet.setFullQuote(quoteText, quoteAuthor)
    }

}