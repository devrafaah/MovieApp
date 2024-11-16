package com.example.movieapp.presenter.main.bottomBar.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.BuildConfig
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.usecase.movie.GetMoviesBySearchUseCase
import com.example.movieapp.util.Constants
import com.example.movieapp.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SearhViewModel @Inject constructor(
    private val getMoviesBySearchUseCase: GetMoviesBySearchUseCase
) : ViewModel(){

    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>> get () = _movieList

    private val _searchState = MutableLiveData<StateView<Unit>>()
    val searchState: LiveData<StateView<Unit>> get() = _searchState

    fun getMoviesGenresBySearch(query: String?){
        viewModelScope.launch {
            try{

                _searchState.postValue(StateView.Loading())

                val movies = getMoviesBySearchUseCase(
                    apiKey = BuildConfig.API_KEY,
                    query = query,
                    language = Constants.Movie.LANGUAGE_PORTUGUESE
                )

                _movieList.postValue(movies)
                _searchState.postValue(StateView.Success(Unit))

            }
            catch (e : HttpException) {
                e.printStackTrace()
                _searchState.postValue(StateView.Error(message = e.message))

            }
            catch (e: Exception) {
                e.printStackTrace()
                _searchState.postValue(StateView.Error(message = e.message))
            }
        }
    }

}