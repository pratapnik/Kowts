package com.ronan.kowts.data

import com.google.gson.annotations.SerializedName

data class QuotesDataModel (
    @SerializedName("text")
    val quoteText: String?,
    @SerializedName("author")
    val quoteAuthor: String?
)