package com.example.movieapp.data.api

import com.example.movieapp.data.model.genre.GetGenres
import com.example.movieapp.data.model.getBase.BasePaginationRemote
import com.example.movieapp.data.model.movie.GetMovie
import com.example.movieapp.data.model.movie_credits.GetCredit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "pt-br",
    ) : GetGenres

    @GET("discover/movie")
    suspend fun moviesByGenre(
        @Query("api_key") apiKey: String,
        @Query("language") language: String?,
        @Query("with_genres") genreId: Int?,
    ) : BasePaginationRemote<List<GetMovie>>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String?,
        @Query("query") query: String?,
    ) : BasePaginationRemote<List<GetMovie>>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int?,
        @Query("api_key") apiKey: String,
        @Query("language") language: String?,
    ) : GetMovie

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int?,
        @Query("api_key") apiKey: String,
        @Query("language") language: String?,
    ) : GetCredit

    @GET("movie/{movie_id}/similar")
    suspend fun getMovieSimilar(
        @Path("movie_id") movieId: Int?,
        @Query("api_key") apiKey: String,
        @Query("language") language: String?,
    ) : BasePaginationRemote<List<GetMovie>>
}