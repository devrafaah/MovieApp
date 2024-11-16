package com.example.movieapp.presenter.main.bottomBar.movie_details.tabLayouts.similar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movieapp.BuildConfig
import com.example.movieapp.domain.usecase.movie.GetMovieSimilarUseCase
import com.example.movieapp.util.Constants
import com.example.movieapp.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SimilarViewModel @Inject constructor(
    private val similarUseCase: GetMovieSimilarUseCase
) : ViewModel() {

    fun getSimilar(movieId: Int) = liveData(Dispatchers.IO){
        try{

            emit(StateView.Loading())

            val movieSimilarList = similarUseCase.invoke(
                movieId = movieId,
                apiKey = BuildConfig.API_KEY,
                language = Constants.Movie.LANGUAGE_PORTUGUESE,
            )

            emit(StateView.Success(movieSimilarList))
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