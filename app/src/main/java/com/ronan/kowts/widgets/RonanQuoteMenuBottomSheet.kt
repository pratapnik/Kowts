package com.ronan.kowts.widgets

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ronan.kowts.R
import com.ronan.kowts.actions.RonanQuoteBottomSheetAction
import kotlinx.android.synthetic.main.ronan_quote_menu_bottom_sheet.*
import java.util.*


class RonanQuoteMenuBottomSheet() : BottomSheetDialogFragment(), TextToSpeech.OnInitListener {

    var actionListener: ActionListener? = null
    lateinit var fullQuoteText: String
    private var tts: TextToSpeech? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.ronan_quote_menu_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tts = TextToSpeech(context, this)

        ivCloseBottomSheet.setOnClickListener {
            dismiss()
        }

        tvReadQuote.setOnClickListener {
            tts!!.speak(fullQuoteText, TextToSpeech.QUEUE_FLUSH, null,"")
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

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.UK)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            } else {
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }
    }

    override fun onDestroy() {
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

}
