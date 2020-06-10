package com.themoviedbapp.di.modules

import androidx.room.Room
import com.themoviedbapp.model.database.TheMovieDatabase
import org.koin.dsl.module

val KoinDatabaseModule = module {

    single {
        Room.databaseBuilder(
            get(),
            TheMovieDatabase::class.java,
            TheMovieDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    factory { get<TheMovieDatabase>().movieDao() }
    factory { get<TheMovieDatabase>().genreDao() }
}