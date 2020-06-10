package com.themoviedbapp.di.modules

import com.themoviedbapp.model.repository.GenreRepository
import com.themoviedbapp.model.repository.GenreRepositoryImpl
import com.themoviedbapp.model.repository.MovieRepository
import com.themoviedbapp.model.repository.MovieRepositoryImpl
import org.koin.dsl.module

val KoinRepositoriesModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
    single<GenreRepository> { GenreRepositoryImpl(get(), get()) }
}