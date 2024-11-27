package com.example.movieapp.data.api

import com.example.movieapp.data.model.genre.GetGenres
import com.example.movieapp.data.model.getBase.BasePaginationRemote
import com.example.movieapp.data.model.movie.GetMovie
import com.example.movieapp.data.model.movie_credits.GetCredit
import com.example.movieapp.data.model.reviewMovie.GetReviewMovie
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {

    @GET("genre/movie/list")
    suspend fun getGenres(): GetGenres

    @GET("discover/movie")
    suspend fun getMoviesByGenrePagination(
        @Query("with_genres") genreId: Int?,
        @Query("page") page: Int?,
    ): BasePaginationRemote<List<GetMovie>>

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("with_genres") genreId: Int?,
    ): BasePaginationRemote<List<GetMovie>>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String?,
        @Query("page") page: Int?,
    ): BasePaginationRemote<List<GetMovie>>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int?,
    ): GetMovie

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int?,
    ): GetCredit

    @GET("movie/{movie_id}/similar")
    suspend fun getMovieSimilar(
        @Path("movie_id") movieId: Int?,
    ): BasePaginationRemote<List<GetMovie>>

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int?,
    ) : BasePaginationRemote<List<GetReviewMovie>>
}