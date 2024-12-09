package com.example.movieapp.domain.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    val id: String? = "",
    val name: String? = "",
    val lastName: String? = "",
    val email: String? = "",
    val phone: String? = "",
    val sex: String? = "",
    val country: String? = "",
    val photoUrl: String? = ""
) : Parcelable
