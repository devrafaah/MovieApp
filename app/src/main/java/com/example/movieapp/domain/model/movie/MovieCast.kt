package com.example.movieapp.domain.model.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class MovieCast(
    val adult: Boolean?,
    val castId: Int?,
    val character: String?,
    val creditId: String?,
    val gender: Int?,
    val id: Int?,
    val knownForDepartment: String?,
    val name: String?,
    val order: Int?,
    val originalName: String?,
    val popularity: Double?,
    val profilePath: String?
) : Parcelable