package com.example.ndsmovieapp.Data.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ndsmovieapp.Data.Api.MovieDBInterface
import com.example.ndsmovieapp.Data.Model.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MovieDetailDataSource (private val apiService : MovieDBInterface, private val compositeDisposable: CompositeDisposable){


    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadedMovieDetailsResponse = MutableLiveData<MovieDetails>()
    val downloadedMovieResponse: LiveData<MovieDetails>
    get() = _downloadedMovieDetailsResponse

    fun fetchMovieDetails(movieId: Int){

        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                    apiService.getMovieDetail(movieId)
                            .subscribeOn(Schedulers.io())
                            .subscribe(
                                    {
                                        _downloadedMovieDetailsResponse.postValue(it)
                                        _networkState.postValue(NetworkState.LOADED)
                                    },
                                    {
                                        _networkState.postValue(NetworkState.ERROR)
                                        Log.e("MovieDetailsDataSource",it.message.toString())
                                    }
                            )
            )

        }
        catch (e: Exception){
            Log.e("MovieDetailsDataSource", e.message.toString())
        }

    }
}