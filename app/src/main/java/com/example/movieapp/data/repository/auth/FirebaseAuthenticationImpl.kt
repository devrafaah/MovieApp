package com.example.movieapp.data.repository.auth

import com.example.movieapp.domain.repository.auth.FirebaseAuthentication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class FirebaseAuthenticationImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): FirebaseAuthentication {
    override suspend fun login(email: String, password: String) {
        return suspendCoroutine { continuation ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resumeWith(Result.success(Unit))
                    } else {
                        val exception = task.exception
                        val error = if (exception is FirebaseAuthException)
                            Exception(exception.errorCode)
                        else
                            exception ?: Exception("Unknown error")
                        continuation.resumeWith(Result.failure(error))
                    }
                }
        }
    }

    override suspend fun register(email: String, password: String) {
        return suspendCoroutine { continuation ->
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resumeWith(Result.success(Unit))
                    } else {
                        val exception = task.exception
                        val error = if (exception is FirebaseAuthException)
                            Exception(exception.errorCode)
                        else
                            exception ?: Exception("Unknown error")
                        continuation.resumeWith(Result.failure(error))
                    }
                }
        }
    }

    override suspend fun forgot(email: String) {
        return suspendCoroutine { continuation ->
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resumeWith(Result.success(Unit))
                    } else {
                        val exception = task.exception
                        val error = if (exception is FirebaseAuthException)
                            Exception(exception.errorCode)
                        else
                            exception ?: Exception("Unknown error")
                        continuation.resumeWith(Result.failure(error))
                    }
                }
        }
    }
}