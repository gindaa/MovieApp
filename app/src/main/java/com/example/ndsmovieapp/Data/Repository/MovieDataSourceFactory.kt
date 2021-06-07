package com.example.ndsmovieapp.Data.Repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.ndsmovieapp.Data.Api.MovieDBInterface
import com.example.ndsmovieapp.Data.Model.Movies
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory (private val apiService : MovieDBInterface, private val compositeDisposable: CompositeDisposable) : DataSource.Factory<Int, Movies>() {

    val movieLiveDatasource = MutableLiveData<MovieDataSource>()
    override fun create(): DataSource<Int, Movies> {
        val movieDataSource = MovieDataSource(apiService,compositeDisposable)

        movieLiveDatasource.postValue(movieDataSource)
        return movieDataSource



    }
}