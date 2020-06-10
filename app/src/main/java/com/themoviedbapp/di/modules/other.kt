package com.themoviedbapp.di.modules

import com.themoviedbapp.view.adapter.GenreAdapter
import com.themoviedbapp.view.adapter.MovieAdapter
import org.koin.dsl.module

val KoinOtherModule = module {
    factory { GenreAdapter() }
    factory { MovieAdapter() }
}