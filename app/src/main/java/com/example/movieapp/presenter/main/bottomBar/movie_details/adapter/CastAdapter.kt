package com.example.movieapp.presenter.main.bottomBar.movie_details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.CastItemBinding
import com.example.movieapp.domain.model.MovieCast

class CastAdapter() : ListAdapter<MovieCast, CastAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<MovieCast>(){
            override fun areItemsTheSame(oldItem: MovieCast, newItem: MovieCast): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieCast, newItem: MovieCast): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CastItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movieCast = getItem(position)

        Glide
            .with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500/${movieCast.profilePath}")
            .into(holder.binding.creditImage)

        holder.binding.creditName.text = movieCast.originalName
        holder.binding.creditJob.text = movieCast.knownForDepartment


    }

    inner class MyViewHolder(val binding: CastItemBinding) : RecyclerView.ViewHolder(binding.root)
}