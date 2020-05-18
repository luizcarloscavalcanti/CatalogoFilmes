package com.luizcarloscavalcanti.catalogodefilmes.data.objects


import com.google.gson.annotations.SerializedName

data class PopularMovie(
    val page: Int,
    @SerializedName("results")
    val movieList: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)