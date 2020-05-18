package com.luizcarloscavalcanti.catalogodefilmes.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.luizcarloscavalcanti.catalogodefilmes.data.api.MovieInterface
import com.luizcarloscavalcanti.catalogodefilmes.data.api.MOVIE_PER_PAGE
import com.luizcarloscavalcanti.catalogodefilmes.data.objects.Movie
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.data_source.genres.ActionDataSource
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.factory.genres.ActionFactory
import io.reactivex.disposables.CompositeDisposable

class ActionRepository(private val apiService: MovieInterface) {

    lateinit var generoPagedList: LiveData<PagedList<Movie>>
    lateinit var actionFactory: ActionFactory

    fun fetchActionList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>> {
        actionFactory =
            ActionFactory(
                apiService,
                compositeDisposable
            )

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(MOVIE_PER_PAGE)
            .build()

        generoPagedList = LivePagedListBuilder(actionFactory, config).build()

        return generoPagedList
    }

    fun getStatusInternet(): LiveData<StatusInternet> {
        return Transformations.switchMap(
            actionFactory.actionLiveDataSource, ActionDataSource::statusInternet
        )
    }
}