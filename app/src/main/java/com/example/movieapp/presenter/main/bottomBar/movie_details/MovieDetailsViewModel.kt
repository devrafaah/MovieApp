package com.example.movieapp.presenter.main.bottomBar.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movieapp.domain.local.usecase.InsertMovieUseCase
import com.example.movieapp.domain.model.movie.Movie
import com.example.movieapp.domain.usecase.movie.GetMovieCreditsUseCase
import com.example.movieapp.domain.usecase.movie.GetMovieDetailsUseCase
import com.example.movieapp.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsUseCase: GetMovieDetailsUseCase,
    private val movieCreditsUseCase: GetMovieCreditsUseCase,
    private val insertMovieUseCase: InsertMovieUseCase
) : ViewModel(){

    private val _movieId = MutableLiveData<Int>()
    val movieId: LiveData<Int> = _movieId

    fun getMovieDetails(movieId: Int?) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())



            val movie = movieDetailsUseCase.invoke(
                movieId = movieId
            )


            emit(StateView.Success(movie))

        } catch (e : HttpException) {
            e.printStackTrace()
            emit(StateView.Error(e.message))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(StateView.Error(e.message))
        }
    }

    fun getMovieCreditsDetails(movieId: Int?) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val creditsMovie = movieCreditsUseCase.invoke(
                movieId = movieId
            )

            emit(StateView.Success(creditsMovie))

        } catch (e : HttpException) {
            e.printStackTrace()
            emit(StateView.Error(e.message))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(StateView.Error(e.message))
        }
    }

    fun insertMovieLocal(movie: Movie) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            insertMovieUseCase(movie)

            emit(StateView.Success(Unit))

        } catch (e: Exception) {
            e.printStackTrace()
            emit(StateView.Error(e.message))
        }
    }

    fun setMovieId(movieId: Int) {
        _movieId.value = movieId
    }
}