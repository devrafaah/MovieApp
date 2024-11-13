package com.example.movieapp.domain.usecase.movie

import com.example.movieapp.data.mapper.toDomain
import com.example.movieapp.data.model.movie.GetMovie
import com.example.movieapp.domain.model.Credit
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.repository.movie.MovieDetailsRepository
import javax.inject.Inject

class GetMovieSimilarUseCase @Inject constructor(
    private val repository: MovieDetailsRepository,
) {
    suspend operator fun invoke(
        apiKey: String,
        language: String?,
        movieId: Int?
    ): List<Movie> {
        return repository.getMovieSimilar(
            apiKey = apiKey,
            language = language,
            movieId = movieId,
        ).map{it.toDomain()}.filter {
            it.posterPath != null && it.backdropPath != null
        }
    }
}