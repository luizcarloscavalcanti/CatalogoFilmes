package com.luizcarloscavalcanti.catalogodefilmes.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.luizcarloscavalcanti.catalogodefilmes.data.api.MovieInterface
import com.luizcarloscavalcanti.catalogodefilmes.data.api.MOVIE_PER_PAGE
import com.luizcarloscavalcanti.catalogodefilmes.data.objects.Movie
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.data_source.MovieDataSource
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.factory.MovieFactory
import io.reactivex.disposables.CompositeDisposable

class PagedMovieRepository(private val apiService: MovieInterface) {

    lateinit var filmePagedList: LiveData<PagedList<Movie>>
    lateinit var filmeDataSourceFactory: MovieFactory

    fun fetchMoviePagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>> {
        filmeDataSourceFactory =
            MovieFactory(
                apiService,
                compositeDisposable
            )

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(MOVIE_PER_PAGE)
            .build()

        filmePagedList = LivePagedListBuilder(filmeDataSourceFactory, config).build()

        return filmePagedList
    }

    fun getStatusInternet(): LiveData<StatusInternet> {
        return Transformations.switchMap(
            filmeDataSourceFactory.filmeLiveDataSource, MovieDataSource::statusInternet
        )
    }
}