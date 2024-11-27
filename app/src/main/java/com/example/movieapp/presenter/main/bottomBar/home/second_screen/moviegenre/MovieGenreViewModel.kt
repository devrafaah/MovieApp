package com.example.movieapp.presenter.main.bottomBar.home.second_screen.moviegenre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.usecase.movie.GetMoviesByGenrePaginationUseCase
import com.example.movieapp.domain.usecase.movie.GetMoviesBySearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieGenreViewModel @Inject constructor(
    private val getMoviesByGenrePaginationUseCase: GetMoviesByGenrePaginationUseCase,
    private val getMoviesBySearchUseCase: GetMoviesBySearchUseCase
): ViewModel() {

    private val _movieList = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val movieList get() = _movieList.asStateFlow()

    private var currentGenreId: Int? = null

    fun getMoviesByGenrePaginationUseCase(genreId: Int?, forceRequest: Boolean) = viewModelScope.launch{
        if(genreId != currentGenreId || forceRequest) {
            currentGenreId = genreId
            getMoviesByGenrePaginationUseCase(
                genreId = genreId
            ).cachedIn(viewModelScope).collectLatest {
                _movieList.emit(it)
            }
        }
    }

    fun getMoviesGenresBySearch(query: String?): Flow<PagingData<Movie>> {
        return getMoviesBySearchUseCase(
            query = query
        ).cachedIn(viewModelScope)
    }

}