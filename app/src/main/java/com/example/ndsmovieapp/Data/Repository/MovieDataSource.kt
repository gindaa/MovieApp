package com.example.ndsmovieapp.Data.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.ndsmovieapp.Data.Api.MovieDBInterface
import com.example.ndsmovieapp.Data.Model.Movies
import com.example.ndsmovieapp.Data.Utils.Credentials.Companion.FIRST_PAGE
import io.reactivex.disposables.CompositeDisposable

class MovieDataSource (private val apiService : MovieDBInterface, private val compositeDisposable: CompositeDisposable) : PageKeyedDataSource<Int, Movies>() {
    private val page = FIRST_PAGE
    val networkState:MutableLiveData<NetworkState> = MutableLiveData()


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movies>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
        apiService.getPopularMovie(page)
                .subscribe(
                        {
                          callback.onResult(it.movieList,null,page+1)
                             networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDataSource", it.message.toString())
                        }

                )
        )

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movies>) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
        apiService.getPopularMovie(params.key)
                .subscribe(
                        {
                            if(it.totalPages >= params.key){
                                callback.onResult(it.movieList,params.key+1)
                                networkState.postValue(NetworkState.LOADED)
                            }
                            else
                                (networkState.postValue(NetworkState.LAST))
                        },
                        {
                            networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDataSource", it.message.toString())
                        }

                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movies>) {

    }


}