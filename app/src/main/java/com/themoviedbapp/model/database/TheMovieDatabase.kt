package com.themoviedbapp.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.themoviedbapp.model.entity.BridgeMovieGenre
import com.themoviedbapp.model.entity.Genre
import com.themoviedbapp.model.entity.Movie

@Database(
    entities = [Movie::class, Genre::class, BridgeMovieGenre::class],
    version = TheMovieDatabase.DATABASE_VERSION,
    exportSchema = TheMovieDatabase.EXPORT_SCHEMA
)
abstract class TheMovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao

    companion object {
        const val DATABASE_VERSION = 1
        const val EXPORT_SCHEMA = false
        const val DATABASE_NAME = "movie_database"
    }
}