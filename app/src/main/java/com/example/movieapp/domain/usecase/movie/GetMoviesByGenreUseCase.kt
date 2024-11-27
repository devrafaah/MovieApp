package com.example.movieapp.domain.usecase.movie

import com.example.movieapp.data.model.movie.GetMovie
import com.example.movieapp.domain.repository.movie.MovieRepository
import javax.inject.Inject

class GetMoviesByGenreUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend operator fun invoke(genreId: Int?): List<GetMovie> {
        return repository.getMoviesByGenre(genreId).results ?: emptyList()
    }
}