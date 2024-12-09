package com.example.movieapp.util

import com.example.movieapp.R
import com.google.firebase.auth.FirebaseAuth

class FirebaseHelper {

    companion object {
        fun getAuth() = FirebaseAuth.getInstance()

        fun isAuthenticated() = getAuth().currentUser != null

        fun getUserId() = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        fun validError(error: String): Int {
            return when {
                error.contains("There is no user record corresponding to this identifier") -> {
                    R.string.account_not_registered_register_fragment
                }
                error.contains("The email address is badly formatted") -> {
                    R.string.invalid_email_register_fragment
                }
                error.contains("The password is invalid") -> {
                    R.string.invalid_password_register_fragment
                }
                error.contains("The email address is already in use by another account") -> {
                    R.string.email_in_use_register_fragment
                }
                error.contains("The supplied auth credential is incorrect, malformed or has expired.") -> {
                    R.string.text_account_error_password_email_login_fragment
                }
                error.contains("Password should be at least 6 characters") -> {
                    R.string.strong_password_register_fragment
                }
                error.contains("Usuario Não Encontrado") -> {
                    R.string.error_user_not_found
                }
                else -> {
                    R.string.error_generic
                }
            }
        }
    }

}