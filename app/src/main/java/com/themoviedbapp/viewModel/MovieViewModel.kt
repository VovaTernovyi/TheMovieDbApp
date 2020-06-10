package com.themoviedbapp.viewModel

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.themoviedbapp.model.custom.SingleLiveEvent
import com.themoviedbapp.model.repository.GenreRepository
import com.themoviedbapp.model.repository.MovieRepository
import org.koin.core.KoinComponent

class MovieViewModel(
    private val movieRepository: MovieRepository,
    private val genreRepository: GenreRepository
) : ViewModel(), KoinComponent {

    private val refreshMoviesLiveData = SingleLiveEvent<Unit>()
    private val refreshGenreLiveData = SingleLiveEvent<Unit>()

    val downloadAndSaveMoviesLiveData = Transformations.switchMap(refreshMoviesLiveData) {
        movieRepository.downloadAndSavePopularMovies()
    }

    val getPopularMoviesLiveData = Transformations.switchMap(refreshMoviesLiveData) {
        movieRepository.getPopularMovies()
    }

    val downloadAndSaveGenresLiveData = Transformations.switchMap(refreshGenreLiveData) {
        genreRepository.downloadAndSaveGenres()
    }

    val getGenreLiveData = Transformations.switchMap(refreshGenreLiveData) {
        genreRepository.getGenres()
    }

    fun refreshMovies() {
        refreshMoviesLiveData.call()
    }

    fun refreshGenre() {
        refreshGenreLiveData.call()
    }

}