package com.luizcarloscavalcanti.catalogodefilmes.data.api

import com.luizcarloscavalcanti.catalogodefilmes.data.objects.MovieDetails
import com.luizcarloscavalcanti.catalogodefilmes.data.objects.PopularMovie
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieInterface {

    @GET("movie/popular")
    fun getPopularFilme(@Query("page") page: Int): Single<PopularMovie>

    @GET("movie/popular")
    fun getGenreFilme(@Query("with_genres") genres: Int): Single<PopularMovie>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>
}