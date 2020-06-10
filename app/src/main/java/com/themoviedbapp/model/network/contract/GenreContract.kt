package com.themoviedbapp.model.network.contract

import com.themoviedbapp.model.entity.GenreResponse
import com.themoviedbapp.model.network.ApiRest
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreContract {

    @GET(ApiRest.GENRES)
    suspend fun getGenres(
        @Query("language") language: String = "en-US"
    ): GenreResponse
}