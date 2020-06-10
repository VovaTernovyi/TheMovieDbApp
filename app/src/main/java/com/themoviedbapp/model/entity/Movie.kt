package com.themoviedbapp.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity
data class Movie(
    @PrimaryKey
    @SerializedName("id")
    val movieId: Int,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("video")
    val video: Boolean,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("genre_ids")
    @TypeConverters(GenreIdListConverter::class)
    val genreIds: List<Int>? = null,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String
)

class GenreIdListConverter {

    @TypeConverter
    fun fromListGenreId(list: List<Int>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toListOfGenreId(json: String): List<Int> {
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(json, listType)
    }
}