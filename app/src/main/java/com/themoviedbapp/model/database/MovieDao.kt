package com.themoviedbapp.model.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.themoviedbapp.model.entity.BridgeMovieGenre
import com.themoviedbapp.model.entity.Genre
import com.themoviedbapp.model.entity.Movie
import com.themoviedbapp.model.entity.MovieWithGenres

@Dao
abstract class MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertMovies(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertMovieGenreBridge(bridgeMovieGenre: BridgeMovieGenre)

    @Query("DELETE FROM BridgeMovieGenre")
    abstract suspend fun removeBridgeMovieGenreData()

    @Query("DELETE FROM Movie")
    abstract suspend fun removeAllMovies()

    @Query("SELECT * FROM Movie")
    abstract fun loadMoviesLiveData(): LiveData<List<Movie>>

    @Query(
        """SELECT * FROM Movie
        INNER JOIN BridgeMovieGenre ON Movie.movieId = BridgeMovieGenre.movieId
        INNER JOIN Genre ON BridgeMovieGenre.genreId = Genre.genreId
        WHERE Genre.genreId == :genreId"""
    )
    abstract fun loadAllMoviesWithGenresById(genreId: Int): LiveData<List<MovieWithGenres>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertGenres(genres: List<Genre>)

    @Query("DELETE FROM Genre")
    abstract suspend fun removeAllGenres()

    @Query("SELECT * FROM Genre")
    abstract fun loadGenresLiveData(): LiveData<List<Genre>>

    @Transaction
    open suspend fun insertMovieWithGenres(movie: Movie, genres: List<Genre>) {
        insertMovie(movie)
        insertGenres(genres)
        genres.forEach {
            insertMovieGenreBridge(BridgeMovieGenre(movieId = movie.movieId, genreId = it.genreId))
        }
    }

    @Transaction
    open suspend fun insertMovieWithGenresBridge(movie: Movie, genreIdList: List<Int>?) {
        insertMovie(movie)
        genreIdList?.forEach {
            insertMovieGenreBridge(BridgeMovieGenre(movieId = movie.movieId, genreId = it))
        }
    }

    @Transaction
    open suspend fun removeAllMoviesWithGenres() {
        removeBridgeMovieGenreData()
        removeAllMovies()
        removeAllGenres()
    }

}