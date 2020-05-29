package com.example.kowts.data

import android.util.Log
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class QuotesApiService {

    val BASE_URL = "https://type.fit/api/quotes/"

    fun getQuotes(): Single<List<QuotesDataModel>> {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(QuotesApi::class.java)
        Log.d("nikhil", BASE_URL)

        return api.getQuotes(BASE_URL)
    }

}