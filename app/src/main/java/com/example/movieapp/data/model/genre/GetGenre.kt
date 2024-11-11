package com.example.movieapp.data.model.genre

import com.google.gson.annotations.SerializedName

data class GetGenre(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String
)
