package com.themoviedbapp.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Genre(
    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "genreId")
    val genreId: Int,
    @SerializedName("name")
    val name: String
)