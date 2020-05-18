package com.luizcarloscavalcanti.catalogodefilmes.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.luizcarloscavalcanti.catalogodefilmes.R
import com.luizcarloscavalcanti.catalogodefilmes.data.api.MovieClient
import com.luizcarloscavalcanti.catalogodefilmes.data.api.MovieInterface
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.*
import com.luizcarloscavalcanti.catalogodefilmes.ui.adapter.ActionAdapter
import com.luizcarloscavalcanti.catalogodefilmes.ui.adapter.AdventureAdapter
import com.luizcarloscavalcanti.catalogodefilmes.ui.adapter.AnimationAdapter
import com.luizcarloscavalcanti.catalogodefilmes.ui.adapter.PopularMovieAdapterList
import com.luizcarloscavalcanti.catalogodefilmes.viewmodel.ActionViewModel
import com.luizcarloscavalcanti.catalogodefilmes.viewmodel.AdventureViewModel
import com.luizcarloscavalcanti.catalogodefilmes.viewmodel.AnimationViewModel
import com.luizcarloscavalcanti.catalogodefilmes.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var actionViewModel: ActionViewModel
    private lateinit var adventureViewModel: AdventureViewModel
    private lateinit var animationViewModel: AnimationViewModel

    lateinit var pagedMovieRepository: PagedMovieRepository
    lateinit var actionRepository: ActionRepository
    lateinit var adventureRepository: AdventureRepository
    lateinit var animationRepository: AnimationRepository

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService: MovieInterface = MovieClient.getClient()

        pagedMovieRepository = PagedMovieRepository(apiService)
        actionRepository = ActionRepository(apiService)
        adventureRepository = AdventureRepository(apiService)
        animationRepository = AnimationRepository(apiService)

        viewModel = getViewModel()
        actionViewModel = getActionViewModel()
        adventureViewModel = getAdventureViewModel()
        animationViewModel = getAnimationViewModel()

        val movieAdapter = PopularMovieAdapterList(this)
        val actionAdapter = ActionAdapter(this)
        val adventureAdaptor = AdventureAdapter(this)
        val animationAdapter = AnimationAdapter(this)

        rv_movie_list.layoutManager = LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false)
        rv_movie_list.setHasFixedSize(true)
        rv_movie_list.adapter = movieAdapter

        rv_movie_list_action.layoutManager =
            LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false)
        rv_movie_list_action.setHasFixedSize(true)
        rv_movie_list_action.adapter = actionAdapter

        rv_movie_list_adventure.layoutManager =
            LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false)
        rv_movie_list_adventure.setHasFixedSize(true)
        rv_movie_list_adventure.adapter = adventureAdaptor

        rv_movie_list_animation.layoutManager =
            LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false)
        rv_movie_list_animation.setHasFixedSize(true)
        rv_movie_list_animation.adapter = animationAdapter

        viewModel.moviePagedList.observe(this, Observer {
            actionAdapter.submitList(it)
        })

        actionViewModel.filmePagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })

        adventureViewModel.filmePagedList.observe(this, Observer {
            adventureAdaptor.submitList(it)
        })

        animationViewModel.filmePagedList.observe(this, Observer {
            animationAdapter.submitList(it)
        })

        viewModel.statusInternet.observe(this, Observer {
            progress_bar_popular.visibility =
                if (viewModel.listIsEmpty() && it == StatusInternet.LOADING) View.VISIBLE else View.GONE
            tv_popular_error.visibility =
                if (viewModel.listIsEmpty() && it == StatusInternet.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()) {
                movieAdapter.setInternetStatus(it)
            }
        })
    }

    private fun getViewModel(): MainViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(pagedMovieRepository) as T
            }
        })[MainViewModel::class.java]
    }

    private fun getActionViewModel(): ActionViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ActionViewModel(actionRepository) as T
            }
        })[ActionViewModel::class.java]
    }

    private fun getAdventureViewModel(): AdventureViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return AdventureViewModel(adventureRepository) as T
            }
        })[AdventureViewModel::class.java]
    }

    private fun getAnimationViewModel(): AnimationViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return AnimationViewModel(animationRepository) as T
            }
        })[AnimationViewModel::class.java]
    }
}
