package com.themoviedbapp.viewModel

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.themoviedbapp.model.custom.SingleLiveEvent
import com.themoviedbapp.model.repository.MovieRepository
import org.koin.core.KoinComponent

class MovieViewModel(private val repository: MovieRepository) : ViewModel(), KoinComponent {

    private val refreshMoviesLiveData = SingleLiveEvent<Unit>()

    val downloadAndSaveMoviesLiveData = Transformations.switchMap(refreshMoviesLiveData) {
        repository.downloadAndSavePopularMovies()
    }

    val downloadAndSaveGenresLiveData = Transformations.switchMap(refreshMoviesLiveData) {
        repository.downloadAndSaveGenres()
    }

    val getPopularMoviesLiveData = Transformations.switchMap(refreshMoviesLiveData) {
        repository.getPopularMovies(28)
    }

    fun refreshMovies() {
        refreshMoviesLiveData.call()
    }

}