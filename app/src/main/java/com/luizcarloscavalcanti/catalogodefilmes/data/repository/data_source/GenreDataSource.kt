package com.luizcarloscavalcanti.catalogodefilmes.data.repository.data_source.genres

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.luizcarloscavalcanti.catalogodefilmes.data.api.MovieInterface
import com.luizcarloscavalcanti.catalogodefilmes.data.api.GENRE_ACTION
import com.luizcarloscavalcanti.catalogodefilmes.data.objects.MovieDetails
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.StatusInternet
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ActionDataSource(
    private val apiService: MovieInterface,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, MovieDetails>() {

    private var action = GENRE_ACTION

    val statusInternet: MutableLiveData<StatusInternet> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieDetails>
    ) {
        statusInternet.postValue(StatusInternet.LOADING)
        compositeDisposable.add(
            apiService.getGenreFilme(action)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.movieList, null, null)
                        statusInternet.postValue(StatusInternet.LOADED)
                    },
                    {
                        statusInternet.postValue(StatusInternet.ERROR)
                        Log.e("ActionDataSource", it.message)
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieDetails>) {
        statusInternet.postValue(StatusInternet.LOADING)
        compositeDisposable.add(
            apiService.getGenreFilme(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if (it.totalPages >= params.key) {
                            callback.onResult(it.movieList, params.key + 1)
                            statusInternet.postValue(StatusInternet.LOADED)
                        } else {
                            statusInternet.postValue(StatusInternet.ENDOFLIST)
                        }
                    },
                    {
                        statusInternet.postValue(StatusInternet.ERROR)
                        Log.e("ActionDataSource", it.message)
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieDetails>) {

    }

}