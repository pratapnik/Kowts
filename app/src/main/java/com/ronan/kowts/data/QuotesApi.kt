package com.ronan.kowts.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface QuotesApi {

    @GET("quotes")
    fun getQuotes(): Single<List<QuotesDataModel>>
}