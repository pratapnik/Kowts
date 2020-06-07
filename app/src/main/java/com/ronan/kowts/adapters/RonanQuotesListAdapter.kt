package com.ronan.kowts.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ronan.kowts.R
import com.ronan.kowts.data.QuotesDataModel
import kotlinx.android.synthetic.main.ronan_quotes_item.view.*

class RonanQuotesListAdapter(val quotesList: ArrayList<QuotesDataModel>) :
    RecyclerView.Adapter<RonanQuotesListAdapter.QuoteViewHolder>() {

    var quoteClickListener: QuoteClickListener? = null

    fun updateQuotesList(newDogsList: ArrayList<QuotesDataModel>) {
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
        var authorName = quotesList[position].quoteAuthor
        val quoteTextString = quotesList[position].quoteText
        holder.view.tvQuoteTextItem.text = quoteTextString
        if (isAuthorAvailable(authorName)) {
            holder.view.tvQuoteAuthorItem.text = "- ".plus(authorName)
        } else {
            authorName = ""
            holder.view.tvQuoteAuthorItem.visibility = View.GONE
        }
        holder.view.tvQuoteSerialNumber.text = (position + 1).toString().plus(".")

        holder.itemView.setOnClickListener {
            if (authorName != "")
                quoteTextString?.let { it1 ->
                    if (authorName != null) {
                        quoteClickListener?.onQuoteClickListener(
                            it1, authorName)
                    }
                }
            else
                quoteTextString?.let { it1 -> quoteClickListener?.onQuoteClickListener(it1, authorName) }
        }
    }

    class QuoteViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    private fun isAuthorAvailable(author: String?): Boolean {
        return author != null
    }

    interface QuoteClickListener {
        fun onQuoteClickListener(quoteText: String, quoteAuthor: String)
    }

}