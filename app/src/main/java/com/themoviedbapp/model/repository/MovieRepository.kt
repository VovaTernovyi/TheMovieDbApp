package com.themoviedbapp.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.themoviedbapp.extension.LiveResource
import com.themoviedbapp.extension.onError
import com.themoviedbapp.model.custom.Resource
import com.themoviedbapp.model.database.MovieDao
import com.themoviedbapp.model.entity.Movie
import com.themoviedbapp.model.network.contract.MovieContract

interface MovieRepository {
    fun getPopularMovies(): LiveData<List<Movie>>
    fun downloadAndSavePopularMovies(): LiveResource<Unit>
}

class MovieRepositoryImpl(
    private val contract: MovieContract,
    private val movieDao: MovieDao
) : MovieRepository {

    override fun getPopularMovies(): LiveData<List<Movie>> =
        movieDao.loadMoviesLiveData()

    override fun downloadAndSavePopularMovies(): LiveResource<Unit> = liveData {
        for (i in 1..11) {
            runCatching {
                emit(Resource.loading())
                val response = contract.getPopularMovies(page = i)
                response.results?.let {
                    movieDao.insertMovies(response.results)
                }
            }.onSuccess {
                emit(Resource.success(it ?: Unit))
            }.onError {
                emit(Resource.error(it))
            }
        }
    }

}