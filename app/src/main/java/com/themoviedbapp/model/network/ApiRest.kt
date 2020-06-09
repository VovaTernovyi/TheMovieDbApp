package com.themoviedbapp.model.network

import com.themoviedbapp.BuildConfig

object ApiRest {

    const val API_BASE_URL = BuildConfig.API_URL

    const val MOVIE_POPULAR = "movie/popular"
    const val GENRES = "genre/movie/list"
}