package com.example.movieapp.presenter.main.bottomBar.home.first_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movieapp.BuildConfig
import com.example.movieapp.data.mapper.toPresentation
import com.example.movieapp.domain.usecase.movie.GetGenresUseCase
import com.example.movieapp.domain.usecase.movie.GetMoviesByGenreUseCase
import com.example.movieapp.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase,
    private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase
) : ViewModel() {

    fun getGenres() = liveData(Dispatchers.IO){
        try{

            emit(StateView.Loading())

            val genres = getGenresUseCase.invoke(
                apiKey = BuildConfig.API_KEY,
                language = null
            ).map {it.toPresentation()}

            emit(StateView.Success(genres))

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
}