package com.example.movieapp.presenter.main.bottomBar.movie_details.tabLayouts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.CommentItemBinding
import com.example.movieapp.domain.model.movie.ReviewMovie
import com.example.movieapp.util.formatCommentDate

class CommentReviewAdapter() : ListAdapter<ReviewMovie, CommentReviewAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<ReviewMovie>(){
            override fun areItemsTheSame(oldItem: ReviewMovie, newItem: ReviewMovie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ReviewMovie, newItem: ReviewMovie): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CommentItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)

        Glide
            .with(holder.itemView.context)
            .load(review.authorDetails?.avatarPath)
            .into(holder.binding.reviewImage)

        holder.binding.reviewName.text = review.authorDetails?.username ?: "Desconhecido"
        holder.binding.reviewComment.text = review.content
        holder.binding.reviewLikes.text = if(review.authorDetails!!.rating != null) {
            review.authorDetails.rating.toString()
        } else {
            "0"
        }
        holder.binding.reviewUpdate.text = formatCommentDate(review.createdAt)




    }

    inner class MyViewHolder(val binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root)
}