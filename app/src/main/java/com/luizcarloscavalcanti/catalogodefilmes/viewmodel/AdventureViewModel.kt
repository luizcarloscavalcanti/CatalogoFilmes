package com.luizcarloscavalcanti.catalogodefilmes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.luizcarloscavalcanti.catalogodefilmes.data.objects.Movie
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.AdventureRepository
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.StatusInternet
import io.reactivex.disposables.CompositeDisposable

class AdventureViewModel (private val adventureRepository: AdventureRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val filmePagedList : LiveData<PagedList<Movie>> by lazy {
        adventureRepository.fetchAdventureList(compositeDisposable)
    }

    val statusInternet : LiveData<StatusInternet> by lazy {
        adventureRepository.getStatusInternet()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}