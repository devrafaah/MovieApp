package com.example.movieapp.presenter.main.bottomBar.movie_details.tabLayouts.comments

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movieapp.domain.usecase.movie.GetMovieReviewsUseCase
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
            Log.i("comentarios", "getCommentsReviews: $movieId")
            val movieCommentsReviewList = getMovieReviewsUseCase.invoke(
                movieId = movieId,
            )
            Log.i("comentarios", "getCommentsReviews: ${movieCommentsReviewList.size}")
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