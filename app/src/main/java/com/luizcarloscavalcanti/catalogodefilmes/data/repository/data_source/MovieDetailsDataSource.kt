package com.luizcarloscavalcanti.catalogodefilmes.data.repository.data_source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.luizcarloscavalcanti.catalogodefilmes.data.api.MovieInterface
import com.luizcarloscavalcanti.catalogodefilmes.data.objects.MovieDetails
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.StatusInternet
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MovieDetailsDataSource(
    private val apiService: MovieInterface,
    private val compositeDisposable: CompositeDisposable
) {

    private val _statusInternet = MutableLiveData<StatusInternet>()
    val statusInternet: LiveData<StatusInternet>
        get() = _statusInternet

    private val _downloadMovieDetails = MutableLiveData<MovieDetails>()
    val downloadDetalhesFilme: LiveData<MovieDetails>
        get() = _downloadMovieDetails

    fun fetchDetalhesFilme(movieId: Int) {
        _statusInternet.postValue(StatusInternet.LOADING)

        try {
            compositeDisposable.add(
                apiService.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadMovieDetails.postValue(it)
                            _statusInternet.postValue(StatusInternet.LOADED)
                        },
                        {
                            _statusInternet.postValue(StatusInternet.ERROR)
                            Log.e("MovieDataSource", it.message)
                        }
                    )
            )
        } catch (e: Exception) {
            Log.e("MovieDataSource", e.message)
        }
    }
}