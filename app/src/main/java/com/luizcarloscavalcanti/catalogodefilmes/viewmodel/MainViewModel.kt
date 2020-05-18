package com.luizcarloscavalcanti.catalogodefilmes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.luizcarloscavalcanti.catalogodefilmes.data.objects.Movie
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.PagedMovieRepository
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.StatusInternet
import io.reactivex.disposables.CompositeDisposable

class MainViewModel (private val pagedMovieRepository: PagedMovieRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val moviePagedList : LiveData<PagedList<Movie>> by lazy {
        pagedMovieRepository.fetchMoviePagedList(compositeDisposable)
    }

    val statusInternet : LiveData<StatusInternet> by lazy {
        pagedMovieRepository.getStatusInternet()
    }

    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}