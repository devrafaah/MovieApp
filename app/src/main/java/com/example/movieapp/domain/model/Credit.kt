package com.example.movieapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Credit(
    val id: Int?,
    val cast: List<MovieCast>?
) : Parcelable
