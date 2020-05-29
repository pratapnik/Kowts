package com.example.kowts.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.kowts.R
import com.example.kowts.data.QuotesDataModel
import kotlinx.android.synthetic.main.ronan_quotes_item.view.*

class RonanQuotesListAdapter(val quotesList: ArrayList<QuotesDataModel>): RecyclerView.Adapter<RonanQuotesListAdapter.QuoteViewHolder>() {

    fun updateQuotesList(newDogsList: ArrayList<QuotesDataModel>){
        quotesList.clear()
        quotesList.addAll(newDogsList)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.ronan_quotes_item, parent, false)
        return QuoteViewHolder(view)
    }

    override fun getItemCount() = quotesList.size

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.view.tvQuoteTextItem.text = quotesList[position].quoteText
        holder.view.tvQuoteAuthorItem.text = quotesList[position].quoteAuthor
    }

    class QuoteViewHolder(var view: View): RecyclerView.ViewHolder(view)

}