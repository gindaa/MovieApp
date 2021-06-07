package com.example.ndsmovieapp.Data.Api

import com.example.ndsmovieapp.Data.Model.MovieDetails
import com.example.ndsmovieapp.Data.Model.PopularMovies
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBInterface {



    @GET("movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") id:Int): Single<MovieDetails>

    @GET("movie/popular")
    fun getPopularMovie(@Query("page")page: Int): Single<PopularMovies>
}