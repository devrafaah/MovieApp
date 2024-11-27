package com.example.movieapp.presenter.main.bottomBar.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.usecase.movie.GetMoviesBySearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearhViewModel @Inject constructor(
    private val getMoviesBySearchUseCase: GetMoviesBySearchUseCase
) : ViewModel(){

    fun getMoviesGenresBySearch(query: String?): Flow<PagingData<Movie>> {
        return getMoviesBySearchUseCase(
            query = query
        ).cachedIn(viewModelScope)
    }

}