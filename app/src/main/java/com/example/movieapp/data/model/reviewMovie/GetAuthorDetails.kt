package com.example.movieapp.data.model.reviewMovie

import com.google.gson.annotations.SerializedName

data class GetAuthorDetails(
    @SerializedName("avatar_path")
    val avatarPath: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("rating")
    val rating: Int?,
    @SerializedName("username")
    val username: String?
)