package com.luizcarloscavalcanti.catalogodefilmes.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luizcarloscavalcanti.catalogodefilmes.R
import com.luizcarloscavalcanti.catalogodefilmes.data.api.IMAGE_URL
import com.luizcarloscavalcanti.catalogodefilmes.data.objects.Movie
import com.luizcarloscavalcanti.catalogodefilmes.data.repository.StatusInternet
import com.luizcarloscavalcanti.catalogodefilmes.ui.MovieActivity
import kotlinx.android.synthetic.main.network_status_item.view.*
import kotlinx.android.synthetic.main.popular_movie_list_item.view.*

class ActionAdapter(public val context: Context) :
    PagedListAdapter<Movie, RecyclerView.ViewHolder>(FilmeDiffCallback()) {

    val FILME_VIEW_TYPE = 1
    private val NETWORK_VIEW_TYPE = 2

    private var internetStatus: StatusInternet? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if (viewType == FILME_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.popular_movie_list_item, parent, false)
            return FilmeDiffCallback.FilmeItemViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.network_status_item, parent, false)
            return FilmeDiffCallback.FilmeItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == FILME_VIEW_TYPE) {
            (holder as FilmeDiffCallback.FilmeItemViewHolder).bind(getItem(position), context)
        } else {
            (holder as InternetStatusViewHolder).bind(internetStatus)
        }
    }

    private fun hasExtraRow(): Boolean {
        return internetStatus != null && internetStatus != StatusInternet.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_VIEW_TYPE
        } else {
            FILME_VIEW_TYPE
        }
    }

    class FilmeDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        class FilmeItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            fun bind(filme: Movie?, context: Context) {
                val filmePosterURL = IMAGE_URL + filme?.posterPath
                Glide.with(itemView.context)
                    .load(filmePosterURL)
                    .into(itemView.iv_movie_poster_cv)

                itemView.setOnClickListener {
                    val intent = Intent(context, MovieActivity::class.java)
                    intent.putExtra("id", filme?.id)
                    context.startActivity(intent)
                }
            }
        }

    }

    class InternetStatusViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(statusInternet: StatusInternet?) {

            if (statusInternet != null && statusInternet == StatusInternet.LOADING) {
                itemView.progress_bar_item.visibility = View.VISIBLE
            } else {
                itemView.progress_bar_item.visibility = View.GONE
            }

            if (statusInternet != null && statusInternet == StatusInternet.LOADING) {
                itemView.error_msg_item.visibility = View.VISIBLE
                itemView.error_msg_item.text = statusInternet.msg
            } else if (statusInternet != null && statusInternet == StatusInternet.ENDOFLIST) {
                itemView.error_msg_item.visibility = View.VISIBLE
                itemView.error_msg_item.text = statusInternet.msg
            } else {
                itemView.error_msg_item.visibility = View.GONE
            }
        }
    }

}