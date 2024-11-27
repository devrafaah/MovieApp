package com.example.movieapp.data.repository.movie

import androidx.paging.PagingSource
import com.example.movieapp.data.api.ServiceApi
import com.example.movieapp.data.model.genre.GetGenres
import com.example.movieapp.data.model.getBase.BasePaginationRemote
import com.example.movieapp.data.model.movie.GetMovie
import com.example.movieapp.data.paging.MovieByGenrePagingSource
import com.example.movieapp.data.paging.SearchMoviePagingSource
import com.example.movieapp.domain.repository.movie.MovieRepository
import javax.inject.Inject

 class MovieRepositoryImpl @Inject constructor(
    private val serviceApi : ServiceApi
) : MovieRepository {

    override suspend fun getGenres(): GetGenres {
        return serviceApi.getGenres()
    }
    override fun getMoviesByGenrePagination( genreId: Int? ): PagingSource<Int, GetMovie> {
        return MovieByGenrePagingSource(serviceApi, genreId)
    }

     override suspend fun getMoviesByGenre(genreId: Int?): BasePaginationRemote<List<GetMovie>> {
         return serviceApi.getMoviesByGenre(genreId)
     }


     override fun getMoviesBySearch( query: String? ): PagingSource<Int, GetMovie> {
        return SearchMoviePagingSource(
            serviceApi = serviceApi,
            query = query
        )
    }

 }