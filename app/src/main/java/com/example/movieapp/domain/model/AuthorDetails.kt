package com.example.movieapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class AuthorDetails(
    val avatarPath: String?,
    val name: String?,
    val rating: Int?,
    val username: String?
) : Parcelable