package com.example.movieapp.data.model.genre

import com.google.gson.annotations.SerializedName

data class GetGenres(

    @SerializedName("genres")
    val genres: List<GetGenre>?

)
