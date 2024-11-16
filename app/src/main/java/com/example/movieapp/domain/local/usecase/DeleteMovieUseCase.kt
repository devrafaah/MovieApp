package com.example.movieapp.domain.local.usecase

import com.example.movieapp.data.mapper.toDomain
import com.example.movieapp.data.mapper.toEntity
import com.example.movieapp.domain.local.repository.MovieLocalRepository
import com.example.movieapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeleteMovieUseCase @Inject constructor(
    private val repository: MovieLocalRepository
) {
    suspend operator fun invoke(movieId: Int?){
        repository.deleteMovie(movieId)
    }
}