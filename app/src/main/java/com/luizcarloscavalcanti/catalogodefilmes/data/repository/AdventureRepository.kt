package com.luizcarloscavalcanti.catalogodefilmes.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.luizcarloscavalcanti.catalogodefilmes.data.api.MovieInterface
import com.luizcarloscavalcanti.catalogodefilmes.data.api.MOVIE_PER_PAGE
import com.luizcarloscavalcanti.catalogodefilmes.data.objects.Movie
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.data_source.genres.AdventureDataSource
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.factory.genres.AdventureFactory
import io.reactivex.disposables.CompositeDisposable

class AdventureRepository(private val apiService: MovieInterface) {

    lateinit var generoPagedList: LiveData<PagedList<Movie>>
    lateinit var generoDataSourceFactory: AdventureFactory

    fun fetchAdventureList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>> {
        generoDataSourceFactory =
            AdventureFactory(
                apiService,
                compositeDisposable
            )

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(MOVIE_PER_PAGE)
            .build()

        generoPagedList = LivePagedListBuilder(generoDataSourceFactory, config).build()

        return generoPagedList
    }

    fun getStatusInternet(): LiveData<StatusInternet> {
        return Transformations.switchMap(
            generoDataSourceFactory.adventureLiveDataSource, AdventureDataSource::statusInternet
        )
    }
}