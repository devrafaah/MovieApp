package com.example.movieapp.presenter.main.bottomBar.home.second_screen.moviegenre.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.MovieLargeItemBinding
import com.example.movieapp.domain.model.Movie

class MovieLargeAdapter(
    private val movieClickListener: (Int?) -> Unit
): ListAdapter<Movie, MovieLargeAdapter.MyViewHolder>(DIFF_CALLBACK)  {
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
        val binding = MovieLargeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = getItem(position)

        holder.binding

        Glide
            .with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500/${movie.posterPath}")
            .into(holder.binding.movieImage)

        holder.itemView.setOnClickListener {
            movieClickListener(movie.id)
        }

    }

    inner class MyViewHolder(val binding: MovieLargeItemBinding) : RecyclerView.ViewHolder(binding.root)
}