package com.luizcarloscavalcanti.catalogodefilmes.data.repository.factory.genres

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.luizcarloscavalcanti.catalogodefilmes.data.api.MovieInterface
import com.luizcarloscavalcanti.catalogodefilmes.data.objects.MovieDetails
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.data_source.genres.ActionDataSource
import io.reactivex.disposables.CompositeDisposable

class ActionFactory(
    private val apiService: MovieInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, MovieDetails>() {

    val actionLiveDataSource = MutableLiveData<ActionDataSource>()
    override fun create(): DataSource<Int, MovieDetails> {
        val actionDataSource =
            ActionDataSource(
                apiService,
                compositeDisposable
            )

        actionLiveDataSource.postValue(actionDataSource)
        return actionDataSource
    }
}