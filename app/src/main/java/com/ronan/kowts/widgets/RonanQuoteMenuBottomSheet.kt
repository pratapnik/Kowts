package com.ronan.kowts.widgets

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ronan.kowts.R
import com.ronan.kowts.actions.RonanQuoteBottomSheetAction
import com.ronan.kowts.utils.makeQuote
import kotlinx.android.synthetic.main.ronan_quote_menu_bottom_sheet.*
import java.util.*


class RonanQuoteMenuBottomSheet() : BottomSheetDialogFragment(), TextToSpeech.OnInitListener {

    var actionListener: ActionListener? = null
    lateinit var quoteText: String
    lateinit var quoteAuthor: String
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

        var fullQuoteText: String
        var quoteToSpeak: String
        if(quoteAuthor!=""){
            tvQuoteDisplayAuthorName.visibility = View.VISIBLE
            fullQuoteText= quoteText.makeQuote(quoteAuthor)
            quoteToSpeak = quoteText.plus(" by ").plus(quoteAuthor)
            tvQuoteDisplayAuthorName.text = "- ".plus(quoteAuthor)
        }
        else{
            quoteToSpeak = quoteText
            fullQuoteText= quoteText
            tvQuoteDisplayAuthorName.visibility = View.GONE
            tvQuoteDisplayAuthorName.text = ""
        }
        tvQuoteDisplayText.text = quoteText

        tvReadQuote.setOnClickListener {
            tts!!.setSpeechRate(0.75F)
            tts!!.speak(quoteToSpeak, TextToSpeech.QUEUE_FLUSH, null,"")
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

    fun setFullQuote(quoteText: String, quoteAuthor: String){
        this.quoteText = quoteText
        this.quoteAuthor = quoteAuthor
    }

    interface ActionListener {
        fun onActionListener(action: RonanQuoteBottomSheetAction, fullQuoteText: String)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {

            val currentLocale = resources.configuration.locale
            var result: Int
            result = if(currentLocale.displayCountry == "India"){
                tts?.setLanguage(Locale("en", "IN"))!!

            } else{
                tts!!.setLanguage(Locale.US)
            }

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
