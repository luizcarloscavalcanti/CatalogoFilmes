package com.luizcarloscavalcanti.catalogodefilmes.data.objects


import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String
)