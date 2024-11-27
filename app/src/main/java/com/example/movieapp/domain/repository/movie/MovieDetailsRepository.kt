package com.example.movieapp.domain.repository.movie

import com.example.movieapp.data.model.movie.GetMovie
import com.example.movieapp.data.model.movie_credits.GetCredit
import com.example.movieapp.data.model.reviewMovie.GetReviewMovie

interface MovieDetailsRepository {

    suspend fun getMoviesDetails(movieId: Int?) : GetMovie

    suspend fun getMovieCredits(movieId: Int?) : GetCredit

    suspend fun getMovieSimilar(movieId: Int?) : List<GetMovie>

    suspend fun getMovieReviews(movieId: Int?) : List<GetReviewMovie>
}