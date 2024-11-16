package com.example.movieapp.presenter.main.bottomBar.movie_details.tabLayouts.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movieapp.BuildConfig
import com.example.movieapp.domain.usecase.movie.GetMovieReviewsUseCase
import com.example.movieapp.util.Constants
import com.example.movieapp.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject


@HiltViewModel
class CommentsViewModel  @Inject constructor(
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase
): ViewModel() {

    fun getCommentsReviews(movieId: Int) = liveData(Dispatchers.IO){
        try{

            emit(StateView.Loading())

            val movieCommentsReviewList = getMovieReviewsUseCase.invoke(
                movieId = movieId,
                apiKey = BuildConfig.API_KEY,
                language = Constants.Movie.LANGUAGE_ENGLISH,
            )

            emit(StateView.Success(movieCommentsReviewList))
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