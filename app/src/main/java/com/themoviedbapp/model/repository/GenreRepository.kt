package com.themoviedbapp.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.themoviedbapp.extension.LiveResource
import com.themoviedbapp.extension.onError
import com.themoviedbapp.model.custom.Resource
import com.themoviedbapp.model.database.GenreDao
import com.themoviedbapp.model.entity.Genre
import com.themoviedbapp.model.network.contract.GenreContract

interface GenreRepository {
    fun getGenres(): LiveData<List<Genre>>
    fun downloadAndSaveGenres(): LiveResource<Unit>
}

class GenreRepositoryImpl(
    private val contract: GenreContract,
    private val genreDao: GenreDao
) : GenreRepository {

    override fun getGenres(): LiveData<List<Genre>> = genreDao.loadGenresLiveData()

    override fun downloadAndSaveGenres(): LiveResource<Unit> = liveData {
        runCatching {
            emit(Resource.loading())
            val response = contract.getGenres()
            response.genres?.let {
                genreDao.insertGenres(it)
            }
        }.onSuccess {
            emit(Resource.success(it ?: Unit))
        }.onError {
            emit(Resource.error(it))
        }
    }

}