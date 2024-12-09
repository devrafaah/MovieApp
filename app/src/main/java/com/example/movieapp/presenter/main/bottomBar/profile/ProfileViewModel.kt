package com.example.movieapp.presenter.main.bottomBar.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movieapp.domain.model.user.User
import com.example.movieapp.domain.usecase.user.GetUserUseCase
import com.example.movieapp.domain.usecase.user.UserUpdateUseCase
import com.example.movieapp.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
) : ViewModel(){




    fun getUser() = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val person = getUserUseCase.invoke()

            emit(StateView.Success(person))
        } catch (ex: Exception){
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message))
        }
    }


}