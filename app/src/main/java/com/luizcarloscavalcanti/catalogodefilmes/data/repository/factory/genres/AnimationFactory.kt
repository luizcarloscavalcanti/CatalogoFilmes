package com.luizcarloscavalcanti.catalogodefilmes.data.repository.factory.genres

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.luizcarloscavalcanti.catalogodefilmes.data.api.MovieInterface
import com.luizcarloscavalcanti.catalogodefilmes.data.objects.Movie
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.data_source.genres.AnimationDataSource
import io.reactivex.disposables.CompositeDisposable

class AnimationFactory(
    private val apiService: MovieInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {

    val animationLiveDataSource = MutableLiveData<AnimationDataSource>()
    override fun create(): DataSource<Int, Movie> {
        val animationDataSource =
            AnimationDataSource(
                apiService,
                compositeDisposable
            )

        animationLiveDataSource.postValue(animationDataSource)
        return animationDataSource
    }
}