package com.ronan.kowts.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ronan.kowts.R
import com.ronan.kowts.actions.RonanQuoteBottomSheetAction
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.ronan_quote_menu_bottom_sheet.*

class RonanQuoteMenuBottomSheet() : BottomSheetDialogFragment() {

    var actionListener: ActionListener? = null
    lateinit var fullQuoteText: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.ronan_quote_menu_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivCloseBottomSheet.setOnClickListener {
            dismiss()
        }
        tvCopyQuote.setOnClickListener {
            actionListener?.onActionListener(RonanQuoteBottomSheetAction.COPY_QUOTE, fullQuoteText)
            dismiss()
        }
        tvShareQuote.setOnClickListener {
            actionListener?.onActionListener(RonanQuoteBottomSheetAction.SHARE_QUOTE, fullQuoteText)
            dismiss()
        }
    }

    fun setFullQuote(fullQuoteText: String){
        this.fullQuoteText = fullQuoteText
    }

    interface ActionListener {
        fun onActionListener(action: RonanQuoteBottomSheetAction, fullQuoteText: String)
    }

}
