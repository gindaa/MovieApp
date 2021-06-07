package com.example.ndsmovieapp.Activity.DetailMovieActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.ndsmovieapp.Data.Api.MovieDBClient
import com.example.ndsmovieapp.Data.Api.MovieDBInterface
import com.example.ndsmovieapp.Data.Model.MovieDetails
import com.example.ndsmovieapp.Data.Repository.NetworkState
import com.example.ndsmovieapp.Data.Utils.Credentials
import com.example.ndsmovieapp.R
import kotlinx.android.synthetic.main.activity_detail_movies.*
import java.text.NumberFormat
import java.util.*


class DetailMovie : AppCompatActivity() {

    private lateinit var viewModel: DetailMovieViewModel
    private lateinit var movieDetailRepository :MovieDetailRepo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movies)

        val movieId: Int = intent.getIntExtra("id",1)
        val apiService : MovieDBInterface = MovieDBClient.getClient()
        movieDetailRepository = MovieDetailRepo(apiService)
        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })
        viewModel.networkState.observe(this, Observer {
            progressBar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            ErrorMessage.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })

    }

    fun bindUI(it: MovieDetails) {
        movie_title.text = it.title
        overview.text = it.overview
        val moviePosterUrl = Credentials.IMAGE_URL + it.poster_path
        Glide.with(this)
                .load(moviePosterUrl)
                .into(movie_poster)


    }

    private fun getViewModel(movieId:Int):DetailMovieViewModel{
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return DetailMovieViewModel(movieDetailRepository,movieId) as T

            }
        })[DetailMovieViewModel::class.java]
    }
}