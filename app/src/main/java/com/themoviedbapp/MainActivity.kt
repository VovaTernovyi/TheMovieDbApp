package com.themoviedbapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.themoviedbapp.databinding.ActivityMainBinding
import com.themoviedbapp.extension.onError
import com.themoviedbapp.viewModel.MovieViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MovieViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel.downloadAndSaveGenresLiveData.observe(this, Observer { response ->
            response.onError { _, _ ->
                Log.e("ERROR: ", "Download genres")
            }
        })
        viewModel.downloadAndSaveMoviesLiveData.observe(this, Observer { response ->
            response.onError { _, _ ->
                Log.e("ERROR: ", "Download movies")
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshMovies()
    }

}
