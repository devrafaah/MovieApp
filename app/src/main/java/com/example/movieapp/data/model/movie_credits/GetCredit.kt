package com.example.movieapp.data.model.movie_credits

import com.google.gson.annotations.SerializedName

data class GetCredit(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("cast")
    val cast: List<GetMovieCast>?

)
