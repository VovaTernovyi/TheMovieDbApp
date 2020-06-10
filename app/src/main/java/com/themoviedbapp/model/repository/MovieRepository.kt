package com.themoviedbapp.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.themoviedbapp.extension.LiveResource
import com.themoviedbapp.extension.onError
import com.themoviedbapp.model.custom.Resource
import com.themoviedbapp.model.database.GenreDao
import com.themoviedbapp.model.database.MovieDao
import com.themoviedbapp.model.entity.Movie
import com.themoviedbapp.model.entity.MovieResponse
import com.themoviedbapp.model.entity.MovieWithGenres
import com.themoviedbapp.model.network.contract.GenreContract
import com.themoviedbapp.model.network.contract.MovieContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface MovieRepository {
    fun getPopularMovies(genreId: Int): LiveData<List<MovieWithGenres>>
    fun downloadAndSavePopularMovies(): LiveResource<Unit>
    fun downloadAndSaveGenres(): LiveResource<Unit>
}

class MovieRepositoryImpl(
    private val movieContract: MovieContract,
    private val genreContract: GenreContract,
    private val movieDao: MovieDao,
    private val genreDao: GenreDao
) : MovieRepository {

    override fun getPopularMovies(genreId: Int): LiveData<List<MovieWithGenres>> =
        loadMoviesWithGenre(genreId)

    override fun downloadAndSavePopularMovies(): LiveResource<Unit> = liveData {
        runCatching {
            emit(Resource.loading())
            val response = movieContract.getPopularMovies(page = page)
            mapAndSaveMovies(response.results)
        }.onSuccess {
            page++
            emit(Resource.success(it ?: Unit))
        }.onError {
            emit(Resource.error(it))
        }
    }

    private suspend fun mapAndSaveMovies(genres: List<MovieResponse>?) =
        withContext(Dispatchers.IO) {
            val pairMoviesGenres = mapMoviesToMovieGenres(genres)
            pairMoviesGenres?.forEach {
                movieDao.insertMovieWithGenresBridge(it.first, it.second)
            }
        }

    private fun mapMoviesToMovieGenres(movies: List<MovieResponse>?) = movies?.map {
        Pair(mapMovieToDbMovie(it), it.genreIds)
    }

    private fun mapMovieToDbMovie(movie: MovieResponse) = Movie(
        movieId = movie.movieId,
        popularity = movie.popularity,
        voteCount = movie.voteCount,
        video = movie.video,
        posterPath = movie.posterPath,
        adult = movie.adult,
        backdropPath = movie.backdropPath,
        originalLanguage = movie.originalLanguage,
        originalTitle = movie.originalTitle,
        title = movie.title,
        voteAverage = movie.voteAverage,
        overview = movie.overview,
        releaseDate = movie.releaseDate
    )

    override fun downloadAndSaveGenres(): LiveResource<Unit> = liveData {
        runCatching {
            emit(Resource.loading())
            val response = genreContract.getGenres()
            response.genres?.let {
                genreDao.insertGenres(it)
            }
        }.onSuccess {
            emit(Resource.success(it ?: Unit))
        }.onError {
            emit(Resource.error(it))
        }
    }

    private fun loadMoviesWithGenre(genreId: Int) =
        movieDao.loadAllMoviesWithGenresById(genreId)

    companion object {
        var page: Int = 1
    }

}