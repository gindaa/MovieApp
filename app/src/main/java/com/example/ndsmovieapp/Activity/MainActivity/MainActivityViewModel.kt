package com.example.ndsmovieapp.Activity.MainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.ndsmovieapp.Data.Model.Movies
import com.example.ndsmovieapp.Data.Repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel (private val movieRepository :MoviePagedRepo) :ViewModel(){

    private val compositeDisposable = CompositeDisposable()

    val moviePagedList : LiveData<PagedList<Movies>> by lazy {
        movieRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }

    fun listIsEmpty():Boolean{
        return moviePagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}