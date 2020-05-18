package com.luizcarloscavalcanti.catalogodefilmes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.luizcarloscavalcanti.catalogodefilmes.data.objects.Movie
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.ActionRepository
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.StatusInternet
import io.reactivex.disposables.CompositeDisposable

class ActionViewModel (private val actionRepository: ActionRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val filmePagedList : LiveData<PagedList<Movie>> by lazy {
        actionRepository.fetchActionList(compositeDisposable)
    }

    val statusInternet : LiveData<StatusInternet> by lazy {
        actionRepository.getStatusInternet()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}