package com.themoviedbapp.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.themoviedbapp.R
import com.themoviedbapp.databinding.ActivityMainBinding
import com.themoviedbapp.view.fragment.PopularMoviesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        showFragment(PopularMoviesFragment.newInstance())
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(binding.mainContainer.id, fragment)
            .commit()
    }

}
