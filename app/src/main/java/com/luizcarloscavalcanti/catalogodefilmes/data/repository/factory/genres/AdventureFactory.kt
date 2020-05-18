package com.luizcarloscavalcanti.catalogodefilmes.data.repository.factory.genres

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.luizcarloscavalcanti.catalogodefilmes.data.api.MovieInterface
import com.luizcarloscavalcanti.catalogodefilmes.data.objects.Movie
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.data_source.genres.AdventureDataSource
import io.reactivex.disposables.CompositeDisposable

class AdventureFactory(
    private val apiService: MovieInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {

    val adventureLiveDataSource = MutableLiveData<AdventureDataSource>()
    override fun create(): DataSource<Int, Movie> {
        val adventureDataSource =
            AdventureDataSource(
                apiService,
                compositeDisposable
            )

        adventureLiveDataSource.postValue(adventureDataSource)
        return adventureDataSource
    }
}
