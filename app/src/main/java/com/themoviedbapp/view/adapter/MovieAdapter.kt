package com.themoviedbapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.themoviedbapp.databinding.ItemMovieBinding
import com.themoviedbapp.model.entity.Movie

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val items = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        items[position].let { movie ->
            holder.binding.run {
                imageUrl = "http://image.tmdb.org/t/p/w185//${movie.posterPath}"
                title = movie.title
                description = movie.overview
            }
        }
    }

    fun clearAndAddItems(itemList: List<Movie>) {
        if (items == itemList) {
            return
        }
        items.clear()
        items.addAll(itemList)
        notifyDataSetChanged()
    }

    class MovieViewHolder(
        val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root)
}