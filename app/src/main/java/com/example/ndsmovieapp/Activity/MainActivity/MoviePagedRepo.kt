package com.example.ndsmovieapp.Activity.MainActivity

import android.graphics.Movie
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.ndsmovieapp.Data.Api.MovieDBInterface
import com.example.ndsmovieapp.Data.Model.Movies
import com.example.ndsmovieapp.Data.Repository.MovieDataSource
import com.example.ndsmovieapp.Data.Repository.MovieDataSourceFactory
import com.example.ndsmovieapp.Data.Repository.NetworkState
import com.example.ndsmovieapp.Data.Utils.Credentials.Companion.POST_PER_PAGE
import io.reactivex.disposables.CompositeDisposable

class MoviePagedRepo (private val apiService : MovieDBInterface){

    lateinit var moviePagedList: LiveData<PagedList<Movies>>
    lateinit var moviesDataSourceFactory : MovieDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable):LiveData<PagedList<Movies>>{
        moviesDataSourceFactory = MovieDataSourceFactory(apiService,compositeDisposable)
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory,config).build()
        return moviePagedList
    }

    fun getNetworkState (): LiveData<NetworkState>{
        return Transformations.switchMap<MovieDataSource,NetworkState>(
            moviesDataSourceFactory.movieLiveDatasource, MovieDataSource::networkState)
    }
}