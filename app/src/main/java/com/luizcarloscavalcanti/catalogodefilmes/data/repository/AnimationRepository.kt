package com.luizcarloscavalcanti.catalogodefilmes.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.luizcarloscavalcanti.catalogodefilmes.data.api.MovieInterface
import com.luizcarloscavalcanti.catalogodefilmes.data.api.MOVIE_PER_PAGE
import com.luizcarloscavalcanti.catalogodefilmes.data.objects.Movie
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.data_source.genres.AnimationDataSource
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.factory.genres.AnimationFactory
import io.reactivex.disposables.CompositeDisposable

class AnimationRepository(private val apiService: MovieInterface) {

    lateinit var generoPagedList: LiveData<PagedList<Movie>>
    lateinit var animationFactory: AnimationFactory

    fun fetchAnimationList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>> {
        animationFactory =
            AnimationFactory(
                apiService,
                compositeDisposable
            )

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(MOVIE_PER_PAGE)
            .build()

        generoPagedList = LivePagedListBuilder(animationFactory, config).build()

        return generoPagedList
    }

    fun getStatusInternet(): LiveData<StatusInternet> {
        return Transformations.switchMap(
            animationFactory.animationLiveDataSource, AnimationDataSource::statusInternet
        )
    }
}