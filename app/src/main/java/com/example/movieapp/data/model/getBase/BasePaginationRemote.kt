package com.example.movieapp.data.model.getBase

import com.google.gson.annotations.SerializedName

data class BasePaginationRemote<out T>(
    @SerializedName("page")
    val page: Int?,

    @SerializedName("results")
    val results: T?,

    @SerializedName("total_results")
    val totalResults: Int?,

    @SerializedName("total_pages")
    val totalPages: Int?,

)
