package com.example.ndsmovieapp.Activity.DetailMovieActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.ndsmovieapp.Data.Model.MovieDetails
import com.example.ndsmovieapp.Data.Repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class DetailMovieViewModel (private val movieRepository:MovieDetailRepo,movieId:Int) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieDetails:LiveData<MovieDetails> by lazy {
        movieRepository.fetchingDetailMovieDetails(compositeDisposable,movieId)
    }
    val networkState : LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}