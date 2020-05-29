package com.example.kowts.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kowts.R
import com.example.kowts.adapters.RonanQuotesListAdapter
import com.example.kowts.data.QuotesDataModel
import com.example.kowts.viewmodels.RonanListViewModel
import kotlinx.android.synthetic.main.ronan_quotes_list_fragment.*

class RonanQuotesListFragment : Fragment() {

    private lateinit var ronanListViewModel: RonanListViewModel
    private val quotesListAdapter = RonanQuotesListAdapter(arrayListOf())

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

        observeViewModel()
    }

    private fun observeViewModel() {
        ronanListViewModel.quotes.observe(viewLifecycleOwner, Observer { quotes ->
            quotes?.let {
                rvQuotesList.visibility = View.VISIBLE
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

}