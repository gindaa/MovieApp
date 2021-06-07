package com.example.ndsmovieapp.Activity.DetailMovieActivity

import androidx.lifecycle.LiveData
import com.example.ndsmovieapp.Data.Api.MovieDBInterface
import com.example.ndsmovieapp.Data.Model.MovieDetails
import com.example.ndsmovieapp.Data.Repository.MovieDetailDataSource
import com.example.ndsmovieapp.Data.Repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieDetailRepo (private val apiService : MovieDBInterface){
    lateinit var movieDetailsNetworkDataSource: MovieDetailDataSource

    fun fetchingDetailMovieDetails (compositeDisposable:CompositeDisposable ,movieId: Int): LiveData<MovieDetails>{

        movieDetailsNetworkDataSource = MovieDetailDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse
    }

    fun getMovieDetailNetworkState(): LiveData<NetworkState>{
        return movieDetailsNetworkDataSource.networkState
    }


}