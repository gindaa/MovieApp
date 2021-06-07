package com.example.ndsmovieapp.Activity.MainActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ndsmovieapp.Activity.DetailMovieActivity.DetailMovie
import com.example.ndsmovieapp.Data.Api.MovieDBClient
import com.example.ndsmovieapp.Data.Api.MovieDBInterface
import com.example.ndsmovieapp.Data.Repository.NetworkState
import com.example.ndsmovieapp.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.network_state_items.*

class MainActivity : AppCompatActivity() {


    private lateinit var  viewModel: MainActivityViewModel
    lateinit var moviePagedRepo: MoviePagedRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService : MovieDBInterface = MovieDBClient.getClient()
        moviePagedRepo = MoviePagedRepo(apiService)
        viewModel = getViewModel()
        val movieAdapter = MainAdapter(this)
        val gridLayoutManager = GridLayoutManager(this ,3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType= movieAdapter.getItemViewType(position)
                if(viewType == movieAdapter.MOVIE_VIEW_TYPE)return 1
                else return 3
            }
        };
        recyclerpopular.layoutManager = gridLayoutManager
        recyclerpopular.setHasFixedSize(true)
        recyclerpopular.adapter = movieAdapter

        viewModel.moviePagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_main.visibility = if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            error_message_main.visibility = if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })

    }
    private fun getViewModel(): MainActivityViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(moviePagedRepo) as T
            }
        })[MainActivityViewModel::class.java]
    }
}