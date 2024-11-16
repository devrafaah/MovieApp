package com.example.movieapp.presenter.main.bottomBar.download.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.MovieDownloadItemBinding
import com.example.movieapp.databinding.MovieItemBinding
import com.example.movieapp.domain.model.Movie

class DownloadMovieAdapter(
    private val context: Context,
    private val detailsClickListener: (Int?) -> Unit,
    private val deleteClickListener: (Int?) -> Unit
) : ListAdapter<Movie, DownloadMovieAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = MovieDownloadItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = getItem(position)

        Glide
            .with(context)
            .load("https://image.tmdb.org/t/p/w200/${movie.posterPath}")
            .into(holder.binding.ivMovie)

        holder.itemView.setOnClickListener {
            detailsClickListener(movie.id)
        }
        holder.binding.ibDelete.setOnClickListener {
            deleteClickListener(movie.id)
        }
        holder.binding.textMovie.text = movie.title
        holder.binding.textDuration.text = movie.runtime.toString()
        holder.binding.textSize.text = movie.runtime.toString()

    }

    inner class MyViewHolder(val binding: MovieDownloadItemBinding) : RecyclerView.ViewHolder(binding.root)
}