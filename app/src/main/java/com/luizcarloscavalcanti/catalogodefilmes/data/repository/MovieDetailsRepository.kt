package com.luizcarloscavalcanti.catalogodefilmes.data.repository

import androidx.lifecycle.LiveData
import com.luizcarloscavalcanti.catalogodefilmes.data.api.MovieInterface
import com.luizcarloscavalcanti.catalogodefilmes.data.objects.MovieDetails
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.data_source.MovieDetailsDataSource
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val apiService: MovieInterface) {

    lateinit var detalhesFilmeDataSource: MovieDetailsDataSource

    fun fetchMovieDetailsList(
        compositeDisposable: CompositeDisposable,
        movieId: Int
    ): LiveData<MovieDetails> {

        detalhesFilmeDataSource =
            MovieDetailsDataSource(
                apiService,
                compositeDisposable
            )
        detalhesFilmeDataSource.fetchDetalhesFilme(movieId)

        return detalhesFilmeDataSource.downloadDetalhesFilme
    }

    fun getDetalhesStatusInternet(): LiveData<StatusInternet> {
        return detalhesFilmeDataSource.statusInternet
    }
}