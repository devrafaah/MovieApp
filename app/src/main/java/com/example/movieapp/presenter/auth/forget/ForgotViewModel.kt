package com.example.movieapp.presenter.auth.forget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movieapp.domain.usecase.auth.ForgotUseCase
import com.example.movieapp.domain.usecase.auth.LoginUseCase
import com.example.movieapp.domain.usecase.auth.RegisterUseCase
import com.example.movieapp.util.StateView
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ForgotViewModel @Inject constructor(
    private val forgotUseCase: ForgotUseCase
) : ViewModel() {

    fun forgot(email: String) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val user = forgotUseCase.invoke(email)

            emit(StateView.Success(user))
        } catch (ex: Exception){
            emit(StateView.Error(message = ex.message))
        }
    }
}