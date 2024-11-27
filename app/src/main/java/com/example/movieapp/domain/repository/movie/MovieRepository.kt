package com.example.movieapp.domain.repository.movie

import androidx.paging.PagingSource
import com.example.movieapp.data.model.genre.GetGenres
import com.example.movieapp.data.model.getBase.BasePaginationRemote
import com.example.movieapp.data.model.movie.GetMovie

interface MovieRepository {



    suspend fun getGenres() : GetGenres

    fun getMoviesByGenrePagination(genreId: Int?) : PagingSource<Int, GetMovie>
    suspend fun getMoviesByGenre(genreId: Int?) : BasePaginationRemote<List<GetMovie>>

    fun getMoviesBySearch(query: String?) : PagingSource<Int, GetMovie>
}