package com.luizcarloscavalcanti.catalogodefilmes.data.repository.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.luizcarloscavalcanti.catalogodefilmes.data.api.MovieInterface
import com.luizcarloscavalcanti.catalogodefilmes.data.objects.Movie
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.data_source.MovieDataSource
import io.reactivex.disposables.CompositeDisposable

class MovieFactory(
    private val apiService: MovieInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {

    val filmeLiveDataSource = MutableLiveData<MovieDataSource>()
    override fun create(): DataSource<Int, Movie> {
        val filmeDataSource =
            MovieDataSource(
                apiService,
                compositeDisposable
            )

        filmeLiveDataSource.postValue(filmeDataSource)
        return filmeDataSource
    }

}