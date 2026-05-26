package com.example.movieapp.domain.usecase.movie

import android.util.Log
import com.example.movieapp.data.mapper.toDomain
import com.example.movieapp.domain.model.movie.Genre
import com.example.movieapp.domain.repository.movie.MovieRepository
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(
    ): List<Genre> {
        if(repository.getGenres().genres == null) Log.d("INFOTESTE", "getGenres: " + repository.getGenres().genres)
        return repository.getGenres().genres?.map { it.toDomain() } ?: emptyList()
    }
}