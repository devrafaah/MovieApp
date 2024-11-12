package com.example.movieapp.data.repository.movie

import com.example.movieapp.data.api.ServiceApi
import com.example.movieapp.data.model.genre.GetGenres
import com.example.movieapp.data.model.movie.GetMovie
import com.example.movieapp.domain.repository.movie.MovieRepository
import javax.inject.Inject

 class MovieRepositoryImpl @Inject constructor(
    private val serviceApi : ServiceApi
) : MovieRepository {

    override suspend fun getGenres(apiKey: String, language: String?): GetGenres {
        return serviceApi.getGenres(
            apiKey = apiKey,
            language = "pt-br"
        )
    }
    override suspend fun getMoviesByGenre(apiKey: String, language: String?, genreId: Int?): List<GetMovie> {
        return serviceApi.moviesByGenre(
            apiKey = apiKey,
            language = language,
            genreId = genreId
        ).results ?: emptyList()
    }
    override suspend fun getMoviesBySearch(apiKey: String, language: String?, query: String?, ): List<GetMovie> {
        return serviceApi.searchMovies(
            apiKey = apiKey,
            language = language,
            query = query,
        ).results ?: emptyList()
    }
    override suspend fun getMoviesDetails(apiKey: String, language: String?, movieId: Int?): GetMovie {
         return serviceApi.getMovieDetails(
             apiKey = apiKey,
             language = language,
             movieId = movieId
         )
     }

 }