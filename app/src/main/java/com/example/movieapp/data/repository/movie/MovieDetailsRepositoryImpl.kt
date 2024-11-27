package com.example.movieapp.data.repository.movie

import com.example.movieapp.data.api.ServiceApi
import com.example.movieapp.data.model.movie.GetMovie
import com.example.movieapp.data.model.movie_credits.GetCredit
import com.example.movieapp.data.model.reviewMovie.GetReviewMovie
import com.example.movieapp.domain.repository.movie.MovieDetailsRepository
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val serviceApi : ServiceApi
) : MovieDetailsRepository {

     override suspend fun getMoviesDetails(
         movieId: Int?
     ): GetMovie {
         return serviceApi.getMovieDetails(
             movieId = movieId
         )
     }

     override suspend fun getMovieCredits(
         movieId: Int?
     ): GetCredit {
         return serviceApi.getMovieCredits(
             movieId = movieId
         )
     }

     override suspend fun getMovieSimilar(
         movieId: Int?
     ): List<GetMovie> {
         return serviceApi.getMovieSimilar(
             movieId = movieId
         ).results ?: emptyList()
     }

     override suspend fun getMovieReviews(
         movieId: Int?,
     ): List<GetReviewMovie> {
         return serviceApi.getMovieReviews(
             movieId = movieId,
         ).results ?: emptyList()
     }


 }