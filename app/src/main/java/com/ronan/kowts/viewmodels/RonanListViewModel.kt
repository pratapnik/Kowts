package com.ronan.kowts.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ronan.kowts.data.QuotesApiService
import com.ronan.kowts.data.QuotesDataModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class RonanListViewModel : ViewModel() {

    private val quotesApiService = QuotesApiService()
    private val disposable = CompositeDisposable()

    val quotes = MutableLiveData<List<QuotesDataModel>>()
    val quotesError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchFromRemote()
    }

    fun refreshShuffle() {
        fetchFromRemoteShuffle()
    }

    private fun fetchFromRemote() {
        loading.value = true

        disposable.add(
            quotesApiService.getQuotes()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<QuotesDataModel>>() {
                    override fun onSuccess(dogsListFromApi: List<QuotesDataModel>) {
                        quotes.value = dogsListFromApi
                        quotesError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        loading.value = false
                        quotesError.value = true
                        e.printStackTrace()
                    }

                })
        )

    }

    private fun fetchFromRemoteShuffle() {
        loading.value = true

        disposable.add(
            quotesApiService.getQuotes()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<QuotesDataModel>>() {
                    override fun onSuccess(dogsListFromApi: List<QuotesDataModel>) {
                        (dogsListFromApi as ArrayList<QuotesDataModel>).shuffle()
                        quotes.value = dogsListFromApi
                        quotesError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        loading.value = false
                        quotesError.value = true
                        e.printStackTrace()
                    }

                })
        )

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}