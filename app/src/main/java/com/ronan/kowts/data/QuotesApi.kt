package com.ronan.kowts.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface QuotesApi {

    @GET
    fun getQuotes(@Url url: String): Single<List<QuotesDataModel>>
}