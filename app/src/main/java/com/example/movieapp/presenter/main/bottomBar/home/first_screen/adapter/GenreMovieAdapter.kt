package com.example.movieapp.presenter.main.bottomBar.home.first_screen.adapter



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.GenreItemBinding
import com.example.movieapp.presenter.model.GenrePresentation

class GenreMovieAdapter(
    private val showAllListener: (Int, String) -> Unit,
    private val movieClickListener: (Int?) -> Unit
): ListAdapter<GenrePresentation, GenreMovieAdapter.MyViewHolder>(
    DIFF_CALLBACK
){

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<GenrePresentation>(){
            override fun areItemsTheSame(oldItem: GenrePresentation, newItem: GenrePresentation): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GenrePresentation, newItem: GenrePresentation): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = GenreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val genre = getItem(position)
        holder.binding.genreNameTitle.text = genre.name

        val movieAdapter = MovieAdapter(
            context = holder.itemView.context,
            movieClickListener = { movieId ->
                movieClickListener(movieId)
            }
        )
        val layoutManager = LinearLayoutManager(
            holder.binding.root.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        holder.binding.textShowAll.setOnClickListener {
            genre.id?.let {
                showAllListener(it, genre.name.toString())
            }
        }

        holder.binding.rvMovie.layoutManager = layoutManager
        holder.binding.rvMovie.setHasFixedSize(true)
        holder.binding.rvMovie.adapter = movieAdapter
        movieAdapter.submitList(genre.movies)

    }

    inner class MyViewHolder(
        val binding: GenreItemBinding
    ) : RecyclerView.ViewHolder(binding.root)


}