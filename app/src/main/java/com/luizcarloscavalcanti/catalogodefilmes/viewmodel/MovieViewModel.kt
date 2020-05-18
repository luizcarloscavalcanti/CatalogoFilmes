package com.luizcarloscavalcanti.catalogodefilmes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.StatusInternet
import com.luizcarloscavalcanti.catalogodefilmes.data.objects.MovieDetails
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.MovieDetailsRepository
import io.reactivex.disposables.CompositeDisposable

class MovieViewModel(private val movieDetailRepository: MovieDetailsRepository, movieId: Int) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val detalhesFilme : LiveData<MovieDetails> by lazy {
        movieDetailRepository.fetchMovieDetailsList(compositeDisposable, movieId)
    }

    val statusInternet : LiveData<StatusInternet> by lazy {
        movieDetailRepository.getDetalhesStatusInternet()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}