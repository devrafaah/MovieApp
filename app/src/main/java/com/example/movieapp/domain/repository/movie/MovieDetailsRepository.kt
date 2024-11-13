package com.example.movieapp.domain.repository.movie

import com.example.movieapp.data.model.genre.GetGenres
import com.example.movieapp.data.model.movie.GetMovie
import com.example.movieapp.data.model.movie_credits.GetCredit

interface MovieDetailsRepository {

    suspend fun getMoviesDetails(
        apiKey: String,
        language: String?,
        movieId: Int?
    ) : GetMovie

    suspend fun getMovieCredits(
        apiKey: String,
        language: String?,
        movieId: Int?
    ) : GetCredit

    suspend fun getMovieSimilar(
        apiKey: String,
        language: String?,
        movieId: Int?
    ) : List<GetMovie>
}