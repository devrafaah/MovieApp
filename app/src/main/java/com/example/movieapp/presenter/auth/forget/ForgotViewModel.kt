package com.example.movieapp.presenter.auth.forget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movieapp.domain.usecase.auth.ForgotUseCase
import com.example.movieapp.domain.usecase.auth.LoginUseCase
import com.example.movieapp.domain.usecase.auth.RegisterUseCase
import com.example.movieapp.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ForgotViewModel @Inject constructor(
    private val forgotUseCase: ForgotUseCase
) : ViewModel() {

    fun forgot(email: String) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            forgotUseCase.invoke(email)

            emit(StateView.Success(Unit))
        } catch (ex: Exception){
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message))
        }
    }
}