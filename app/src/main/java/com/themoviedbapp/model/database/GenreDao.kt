package com.themoviedbapp.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.themoviedbapp.model.entity.Genre

@Dao
abstract class GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertGenres(genres: List<Genre>)

    @Query("DELETE FROM Genre")
    abstract suspend fun removeAllGenres()

    @Query("SELECT * FROM Genre")
    abstract fun loadGenresLiveData(): LiveData<List<Genre>>
}