package com.themoviedbapp.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.themoviedbapp.model.entity.Genre
import com.themoviedbapp.model.entity.GenreIdListConverter
import com.themoviedbapp.model.entity.Movie

@Database(
    entities = [Movie::class, Genre::class],
    version = TheMovieDatabase.DATABASE_VERSION,
    exportSchema = TheMovieDatabase.EXPORT_SCHEMA
)
@TypeConverters(GenreIdListConverter::class)
abstract class TheMovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao

    companion object {
        const val DATABASE_VERSION = 1
        const val EXPORT_SCHEMA = false
        const val DATABASE_NAME = "movie_database"
    }
}