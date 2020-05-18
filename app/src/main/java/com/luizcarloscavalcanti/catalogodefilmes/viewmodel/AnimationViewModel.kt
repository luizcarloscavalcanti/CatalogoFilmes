package com.luizcarloscavalcanti.catalogodefilmes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.luizcarloscavalcanti.catalogodefilmes.data.objects.Movie
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.AnimationRepository
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.StatusInternet
import io.reactivex.disposables.CompositeDisposable

class AnimationViewModel (private val animationRepository: AnimationRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val filmePagedList : LiveData<PagedList<Movie>> by lazy {
        animationRepository.fetchAnimationList(compositeDisposable)
    }

    val statusInternet : LiveData<StatusInternet> by lazy {
        animationRepository.getStatusInternet()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}