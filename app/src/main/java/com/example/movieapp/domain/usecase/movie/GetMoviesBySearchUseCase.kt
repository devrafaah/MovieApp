package com.example.movieapp.domain.usecase.movie

import com.example.movieapp.data.mapper.toDomain
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.repository.movie.MovieRepository
import javax.inject.Inject

class GetMoviesBySearchUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(
        apiKey: String,
        language: String?,
        query: String?
    ): List<Movie> {
        return repository.getMoviesBySearch(
            apiKey = apiKey,
            language = language,
            query = query,
        ).filter { it.backdropPath != null }.map { it.toDomain() }
    }
}