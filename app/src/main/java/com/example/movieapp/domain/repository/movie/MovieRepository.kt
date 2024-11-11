package com.example.movieapp.domain.repository.movie

import com.example.movieapp.data.model.genre.GetGenres
import com.example.movieapp.data.model.movie.GetMovie

interface MovieRepository {

    suspend fun getGenres(apiKey: String, language: String?) : GetGenres

    suspend fun getMoviesByGenre(
        apiKey: String,
        language: String?,
        genreId: Int?,
    ) : List<GetMovie>


    suspend fun getMoviesBySearch(
        apiKey: String,
        language: String?,
        query: String?,
    ) : List<GetMovie>

    suspend fun getMoviesDetails(
        apiKey: String,
        language: String?,
        movieId: Int?
    ) : GetMovie
}