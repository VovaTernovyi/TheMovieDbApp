package com.themoviedbapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.themoviedbapp.databinding.FragmentPopularMoviesBinding
import com.themoviedbapp.extension.onError
import com.themoviedbapp.view.adapter.GenreAdapter
import com.themoviedbapp.viewModel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_popular_movies.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class PopularMoviesFragment : Fragment() {

    private lateinit var binding: FragmentPopularMoviesBinding
    private val viewModel: MovieViewModel by viewModel()

    private val genreAdapter: GenreAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentPopularMoviesBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popular_movies_recycler_view.apply {
            adapter = genreAdapter
        }
        setupObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshGenre()
        viewModel.refreshMovies()
    }

    private fun setupObservers() = viewModel.run {
        viewModel.downloadAndSaveGenresLiveData.observe(viewLifecycleOwner, Observer { response ->
            response.onError { _, _ ->
                Log.e("ERROR: ", "Download genres")
            }
        })
        viewModel.downloadAndSaveMoviesLiveData.observe(viewLifecycleOwner, Observer { response ->
            response.onError { _, _ ->
                Log.e("ERROR: ", "Download movies")
            }
        })
        viewModel.getGenreLiveData.observe(viewLifecycleOwner, Observer {
            genreAdapter.clearAndAddItems(it)
        })
        viewModel.getPopularMoviesLiveData.observe(viewLifecycleOwner, Observer {
            genreAdapter.clearAndAddMovies(it)
        })
    }

    companion object {

        fun newInstance() = PopularMoviesFragment()
    }

}
