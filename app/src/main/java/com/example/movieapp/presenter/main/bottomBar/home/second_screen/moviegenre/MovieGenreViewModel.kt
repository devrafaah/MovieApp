package com.example.movieapp.presenter.main.bottomBar.home.second_screen.moviegenre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movieapp.BuildConfig
import com.example.movieapp.domain.usecase.movie.GetMoviesByGenreUseCase
import com.example.movieapp.domain.usecase.movie.GetMoviesBySearchUseCase
import com.example.movieapp.util.Constants
import com.example.movieapp.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject


@HiltViewModel
class MovieGenreViewModel @Inject constructor(
    private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase,
    private val getMoviesBySearchUseCase: GetMoviesBySearchUseCase
): ViewModel() {

    fun getMoviesGenres(genresId: Int?) = liveData(Dispatchers.IO){
        try{

            emit(StateView.Loading())

            val movies = getMoviesByGenreUseCase.invoke(
                genreId = genresId,
                apiKey = BuildConfig.API_KEY,
                language = null
            )

            emit(StateView.Success(movies))

        }
        catch (e : HttpException) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))

        }
        catch (e: Exception) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        }
    }

    fun getMoviesGenresBySearch(query: String?) = liveData(Dispatchers.IO){
        try{

            emit(StateView.Loading())

            val movies = getMoviesBySearchUseCase.invoke(
                apiKey = BuildConfig.API_KEY,
                query = query,
                language = Constants.Movie.LANGUAGE_PORTUGUESE
            )

            emit(StateView.Success(movies))

        }
        catch (e : HttpException) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))

        }
        catch (e: Exception) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        }
    }

}