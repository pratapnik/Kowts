package com.example.kowts.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kowts.R
import com.example.kowts.actions.RonanQuoteBottomSheetAction
import com.example.kowts.adapters.RonanQuotesListAdapter
import com.example.kowts.data.QuotesDataModel
import com.example.kowts.utils.copyText
import com.example.kowts.utils.sendText
import com.example.kowts.utils.showSnackBar
import com.example.kowts.viewmodels.RonanListViewModel
import com.example.kowts.widgets.RonanQuoteMenuBottomSheet
import kotlinx.android.synthetic.main.ronan_quotes_list_fragment.*
import java.util.*
import kotlin.collections.ArrayList

class RonanQuotesListFragment : Fragment(), RonanQuotesListAdapter.QuoteClickListener,
    RonanQuoteMenuBottomSheet.ActionListener {

    private lateinit var ronanListViewModel: RonanListViewModel
    private val quotesListAdapter = RonanQuotesListAdapter(arrayListOf())
    private lateinit var ronanQuoteMenuBottomSheet: RonanQuoteMenuBottomSheet

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.ronan_quotes_list_fragment, container, false)
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

        srlQuotesList.setOnRefreshListener {
            rvQuotesList.visibility = View.GONE
            tvErrorOccured.visibility = View.GONE
            pbLoadData.visibility = View.VISIBLE
            ronanListViewModel.refresh()
            srlQuotesList.isRefreshing = false
        }

        quotesListAdapter.quoteClickListener = this

        observeViewModel()
    }

    private fun observeViewModel() {
        ronanListViewModel.quotes.observe(viewLifecycleOwner, Observer { quotes ->
            quotes?.let {
                rvQuotesList.visibility = View.VISIBLE
//                (quotes as ArrayList<QuotesDataModel>).shuffle()
                quotesListAdapter.updateQuotesList(quotes as ArrayList<QuotesDataModel>)
            }
        })

        ronanListViewModel.quotesError.observe(viewLifecycleOwner, Observer {
            it?.let {
                tvErrorOccured.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        ronanListViewModel.loading.observe(viewLifecycleOwner, Observer {
            it?.let {
                pbLoadData.visibility = if (it) View.VISIBLE else View.GONE

                if (it) {
                    rvQuotesList.visibility = View.GONE
                    tvErrorOccured.visibility = View.GONE
                }
            }
        })
    }

    override fun onQuoteClickListener(fullQuoteText: String) {
        ronanQuoteMenuBottomSheet.show(
            childFragmentManager,
            resources.getString(R.string.label_open_quote_menu)
        )
        ronanQuoteMenuBottomSheet.setFullQuote(fullQuoteText)
    }

    override fun onActionListener(action: RonanQuoteBottomSheetAction, fullQuoteText: String) {
        Log.d("nikhil action", action.name)
        when (action) {
            RonanQuoteBottomSheetAction.COPY_QUOTE -> {
                activity?.copyText(fullQuoteText)
                layoutQuotesList.showSnackBar("Quote is copied to clipboard")
            }
            RonanQuoteBottomSheetAction.SHARE_QUOTE -> {
                activity?.sendText(fullQuoteText)
            }
        }
    }

}