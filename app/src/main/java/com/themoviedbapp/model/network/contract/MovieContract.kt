package com.themoviedbapp.model.network.contract

import com.themoviedbapp.model.entity.BaseResponse
import com.themoviedbapp.model.entity.MovieResponse
import com.themoviedbapp.model.network.ApiRest
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieContract {

    @GET(ApiRest.MOVIE_POPULAR)
    suspend fun getPopularMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): BaseResponse<MovieResponse>
}