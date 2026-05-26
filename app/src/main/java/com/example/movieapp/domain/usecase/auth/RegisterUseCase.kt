package com.example.movieapp.domain.usecase.auth

import com.example.movieapp.domain.model.user.User
import com.example.movieapp.domain.repository.auth.FirebaseAuthentication
import com.example.movieapp.domain.repository.user.UserRepository
import com.example.movieapp.util.FirebaseHelper
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val firebaseAuthentication: FirebaseAuthentication,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String) {
        firebaseAuthentication.register(email, password)
        val user = User(
            id = FirebaseHelper.getUserId(),
            email = email
        )
        userRepository.update(user)
    }
}