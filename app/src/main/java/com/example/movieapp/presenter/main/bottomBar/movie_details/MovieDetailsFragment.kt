package com.example.movieapp.presenter.main.bottomBar.movie_details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMovieDetailsBinding
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.util.StateView
import com.example.movieapp.util.initToolbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: MovieDetailsFragmentArgs by navArgs()

    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }
    private fun initListeners() {
        initToolbar(toolbar = binding.toolbar, lightIcon = true)
        getMovieDetails()
    }


    private fun getMovieDetails() {
        viewModel.getMovieDetails(movieId = args.movieId).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {

                }
                is StateView.Success -> {
                    configData(stateView.data)
                }
                is StateView.Error -> {

                }

            }
        }
    }
    private fun configData(movie: Movie?) {
        Glide
            .with(requireContext())
            .load("https://image.tmdb.org/t/p/w500${movie?.backdropPath}")
            .into(binding.moviePoster)

        binding.movieTitle.text = movie?.title
        binding.movieVoteAverage.text = String.format(Locale.US, "%.1f" , movie?.voteAverage ?: 0.0)
        binding.movieReleaseDate.text = movie?.releaseDate?.split("-")?.get(0) ?: "não informado"

        binding.movieProductionCountry.text = if (movie?.productionCountries?.isNotEmpty() == true) {
            movie.productionCountries[0].name.toString()
        } else {
            "País não informado"
        }

        binding.movieLang.text = movie?.originalLanguage?.uppercase() ?: "Não informado"

        val genres = movie?.genres?.joinToString(", ") { it.name } ?: "Não informado"
        binding.movieGenreList.text = String.format(Locale.US, "Genre: $genres")
        binding.movieSinopse.text = movie?.overview ?: "Sinopse não disponível"


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}