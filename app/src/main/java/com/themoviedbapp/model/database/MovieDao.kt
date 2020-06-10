package com.themoviedbapp.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.themoviedbapp.model.entity.Movie

@Dao
abstract class MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertMovies(movies: List<Movie>)

    @Query("DELETE FROM Movie")
    abstract suspend fun removeAllMovies()

    @Query("SELECT * FROM Movie")
    abstract fun loadMoviesLiveData(): LiveData<List<Movie>>
}