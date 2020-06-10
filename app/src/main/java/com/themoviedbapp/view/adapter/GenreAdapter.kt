package com.themoviedbapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.themoviedbapp.databinding.ItemMovieTypeBinding
import com.themoviedbapp.model.entity.Genre
import com.themoviedbapp.model.entity.Movie
import kotlinx.android.synthetic.main.item_movie_type.view.*

class GenreAdapter : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    private val items = mutableListOf<Genre>()
    private val movies = mutableListOf<Movie>()

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder =
        GenreViewHolder(
            ItemMovieTypeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        items[position].let { item ->
            holder.binding.run {
                title = item.name
            }
        }
        val childLayoutManger = LinearLayoutManager(
            holder.itemView.item_movie_recycler_view.context,
            RecyclerView.HORIZONTAL,
            false
        )
        holder.itemView.item_movie_recycler_view.apply {
            layoutManager = childLayoutManger
            val childAdapter = MovieAdapter()
            adapter = childAdapter
            childAdapter.clearAndAddItems(getMoviesByGenre(items[position].genreId))
            setRecycledViewPool(recycledViewPool)
        }
    }

    private fun getMoviesByGenre(genreId: Int): ArrayList<Movie> {
        val result = ArrayList<Movie>()
        movies.forEach {
            it.genreIds?.let { genreIdList ->
                if (genreIdList.contains(genreId)) {
                    result.add(it)
                }
            }
        }
        return result
    }

    fun clearAndAddItems(itemList: List<Genre>) {
        if (items == itemList) {
            return
        }
        items.clear()
        items.addAll(itemList)
        notifyDataSetChanged()
    }

    fun clearAndAddMovies(movieList: List<Movie>) {
        if (movies == movieList) {
            return
        }
        movies.clear()
        movies.addAll(movieList)
        notifyDataSetChanged()
    }

    class GenreViewHolder(
        val binding: ItemMovieTypeBinding
    ) : RecyclerView.ViewHolder(binding.root)
}