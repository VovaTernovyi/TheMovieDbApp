package com.themoviedbapp.model.repository

import androidx.lifecycle.liveData
import com.themoviedbapp.extension.LiveResource
import com.themoviedbapp.extension.onError
import com.themoviedbapp.model.custom.Resource
import com.themoviedbapp.model.network.contract.MovieContract

interface MovieRepository {
    //    fun getPopularMovies(): LiveData<List<Any>>
    fun downloadAndSavePopularMovies(): LiveResource<Unit>
}

class MovieRepositoryImpl(
    private val contract: MovieContract
) : MovieRepository {

//    override fun getPopularMovies(): LiveData<List<Any>> {
//
//    }

    override fun downloadAndSavePopularMovies(): LiveResource<Unit> = liveData {
        runCatching {
            emit(Resource.loading())
            val movieList = contract.getPopularMovies(page = page)
        }.onSuccess {
            page++
            emit(Resource.success(it))
        }.onError {
            emit(Resource.error(it))
        }
    }

    companion object {
        var page: Int = 1
    }

}