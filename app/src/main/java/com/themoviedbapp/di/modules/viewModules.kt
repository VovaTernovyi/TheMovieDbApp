package com.themoviedbapp.di.modules

import com.themoviedbapp.viewModel.MovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val KoinArchitectureComponentViewModels = module {
    viewModel { MovieViewModel(get()) }
}