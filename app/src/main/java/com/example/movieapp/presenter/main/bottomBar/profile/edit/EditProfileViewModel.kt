package com.example.movieapp.presenter.main.bottomBar.profile.edit

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movieapp.R
import com.example.movieapp.domain.model.user.User
import com.example.movieapp.domain.usecase.user.GetUserUseCase
import com.example.movieapp.domain.usecase.user.SaveUserImageUseCase
import com.example.movieapp.domain.usecase.user.UserUpdateUseCase
import com.example.movieapp.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userUpdateUseCase: UserUpdateUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val saveUserImageUseCase: SaveUserImageUseCase
) : ViewModel(){


    private val _validateData = MutableLiveData<Pair<Boolean, Int?>>()
    val validateData: MutableLiveData<Pair<Boolean, Int?>> = _validateData


    fun userUpdate(user: User) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            userUpdateUseCase.invoke(user)

            emit(StateView.Success(user))
        } catch (ex: Exception){
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message))
        }
    }

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

    fun saveUserImage(uri: Uri) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val url = saveUserImageUseCase.invoke(uri)

            emit(StateView.Success(url))
        } catch (ex: Exception){
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message))
        }
    }

    fun validateData(firstName: String, lastName: String, phone: String, sex: String, country: String) {
        if(firstName.isBlank()){
            validateData.value = Pair(false, R.string.fill_all_fields)
            return
        }
        if(lastName.isBlank()){
            validateData.value = Pair(false, R.string.fill_all_fields)
            return
        }
        if(phone.isBlank()){
            validateData.value = Pair(false, R.string.fill_all_fields)
            return
        }
        if(sex.isBlank()){
            validateData.value = Pair(false, R.string.fill_all_fields)
            return
        }
        if(country.isBlank()){
            validateData.value = Pair(false, R.string.fill_all_fields)
            return
        }

        validateData.value = Pair(true, null)

    }


}