package com.example.movieapp.domain.repository.movie

import com.example.movieapp.data.model.genre.GetGenres
import com.example.movieapp.data.model.getBase.BasePaginationRemote
import com.example.movieapp.data.model.movie.GetMovie
import com.example.movieapp.data.model.movie_credits.GetCredit
import com.example.movieapp.data.model.reviewMovie.GetReviewMovie
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

    suspend fun getMovieReviews(
        movieId: Int?,
        apiKey: String,
        language: String?,
    ) : List<GetReviewMovie>
}