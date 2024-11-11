package com.example.movieapp.data.model.countries

import com.google.gson.annotations.SerializedName

data class CountryResponse(

    @SerializedName("iso_3166_1")
    val iso31661: String?,

    @SerializedName("name")
    val name: String?
)
