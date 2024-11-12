package com.example.movieapp.data.model.movie

import android.os.Parcelable
import com.example.movieapp.data.model.countries.CountryResponse
import com.example.movieapp.data.model.genre.GetGenre
import com.example.movieapp.data.model.movie_credits.GetCredit
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class GetMovie(
    @SerializedName("adult")
    val adult: Boolean?,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("genre_ids")
    val genreIds: List<Int>?,

    @SerializedName("id")
    val id: Int?,

    @SerializedName("original_language")
    val originalLanguage: String?,

    @SerializedName("original_title")
    val originalTitle: String?,

    @SerializedName("overview")
    val overview: String?,

    @SerializedName("popularity")
    val popularity: Float?,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("video")
    val video: Boolean?,

    @SerializedName("vote_average")
    val voteAverage: Float?,

    @SerializedName("vote_count")
    val voteCount: Int?,

    @SerializedName("production_countries")
    val productionCountries: List<CountryResponse>?,

    @SerializedName("genres")
    val genres: List<GetGenre>?,
)
