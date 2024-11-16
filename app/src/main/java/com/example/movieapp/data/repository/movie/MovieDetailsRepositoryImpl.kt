package com.example.movieapp.data.repository.movie

import com.example.movieapp.data.api.ServiceApi
import com.example.movieapp.data.model.genre.GetGenres
import com.example.movieapp.data.model.movie.GetMovie
import com.example.movieapp.data.model.movie_credits.GetCredit
import com.example.movieapp.data.model.reviewMovie.GetReviewMovie
import com.example.movieapp.domain.repository.movie.MovieDetailsRepository
import com.example.movieapp.domain.repository.movie.MovieRepository
import javax.inject.Inject

 class MovieDetailsRepositoryImpl @Inject constructor(
    private val serviceApi : ServiceApi
) : MovieDetailsRepository {

     override suspend fun getMoviesDetails(
         apiKey: String,
         language: String?,
         movieId: Int?
     ): GetMovie {
         return serviceApi.getMovieDetails(
             apiKey = apiKey,
             language = language,
             movieId = movieId
         )
     }

     override suspend fun getMovieCredits(
         apiKey: String,
         language: String?,
         movieId: Int?
     ): GetCredit {
         return serviceApi.getMovieCredits(
             apiKey = apiKey,
             language = language,
             movieId = movieId
         )
     }

     override suspend fun getMovieSimilar(
         apiKey: String,
         language: String?,
         movieId: Int?
     ): List<GetMovie> {
         return serviceApi.getMovieSimilar(
             apiKey = apiKey,
             language = language,
             movieId = movieId
         ).results ?: emptyList()
     }

     override suspend fun getMovieReviews(
         movieId: Int?,
         apiKey: String,
         language: String?
     ): List<GetReviewMovie> {
         return serviceApi.getMovieReviews(
             movieId = movieId,
             apiKey = apiKey,
             language = language
         ).results ?: emptyList()
     }


 }