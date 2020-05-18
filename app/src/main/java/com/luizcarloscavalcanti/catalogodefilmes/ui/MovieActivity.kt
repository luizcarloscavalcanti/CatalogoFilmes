package com.luizcarloscavalcanti.catalogodefilmes.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.luizcarloscavalcanti.catalogodefilmes.R
import com.luizcarloscavalcanti.catalogodefilmes.data.api.MovieInterface
import com.luizcarloscavalcanti.catalogodefilmes.data.api.IMAGE_URL
import com.luizcarloscavalcanti.catalogodefilmes.data.api.MovieClient
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.StatusInternet
import com.luizcarloscavalcanti.catalogodefilmes.data.objects.MovieDetails
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.MovieDetailsRepository
import com.luizcarloscavalcanti.catalogodefilmes.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_filme.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class MovieActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieViewModel
    private lateinit var filmeRepositorio: MovieDetailsRepository

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filme)

        val movieId: Int = intent.getIntExtra("id", 1)

        val apiService: MovieInterface = MovieClient.getClient()
        filmeRepositorio =
            MovieDetailsRepository(
                apiService
            )

        viewModel = getViewModel(movieId)

        viewModel.detalhesFilme.observe(this, Observer {
            bindUI(it)
        })

        viewModel.statusInternet.observe(this, Observer {
            progress_bar.visibility = if (it == StatusInternet.LOADING) View.VISIBLE else View.GONE
            tv_error.visibility = if (it == StatusInternet.ERROR) View.VISIBLE else View.GONE
            constraint_layout.visibility =
                if (tv_error.visibility == View.VISIBLE ||
                    progress_bar.visibility == View.VISIBLE
                )
                    View.GONE else View.VISIBLE
        })

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindUI(it: MovieDetails) {
        movie_title.text = it.title
        movie_tagline.text = it.tagline

        var dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val releaseDate: Date = dateFormat.parse(it.releaseDate)
        dateFormat = SimpleDateFormat("dd/MM/yyyy")
        movie_release_date.text = dateFormat.format(releaseDate)

        movie_vote_average.text = it.voteAverage.toString()
        movie_runtime.text = "- " + it.runtime.toString() + " minutos"
        movie_overview.text = it.overview
        movie_original_title.text = it.originalTitle
        movie_genre.text = ""

        for (i in it.genres.indices) {
            movie_genre.append(it.genres[i].name + "\n")
        }

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formatCurrency.format(it.budget)
        movie_revenue.text = formatCurrency.format(it.revenue)

        val filmeCapaURL: String = IMAGE_URL + it.posterPath
        Glide.with(this)
            .load(filmeCapaURL)
            .into(iv_movie_cover)

        val filmePosterURL: String = IMAGE_URL + it.backdropPath
        Glide.with(this)
            .load(filmePosterURL)
            .into(iv_movie_poster)
    }

    private fun getViewModel(movieId: Int): MovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieViewModel(
                    filmeRepositorio,
                    movieId
                ) as T
            }
        })[MovieViewModel::class.java]
    }
}
