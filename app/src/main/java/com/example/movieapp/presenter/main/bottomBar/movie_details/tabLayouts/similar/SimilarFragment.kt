package com.example.movieapp.presenter.main.bottomBar.movie_details.tabLayouts.similar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.MainGraphDirections
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentSimilarBinding
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.presenter.main.bottomBar.home.first_screen.adapter.MovieAdapter
import com.example.movieapp.presenter.main.bottomBar.home.second_screen.moviegenre.adapter.MovieLargeAdapter
import com.example.movieapp.presenter.main.bottomBar.movie_details.MovieDetailsViewModel
import com.example.movieapp.util.StateView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SimilarFragment() : Fragment() {

    private var _binding: FragmentSimilarBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieLargeAdapter: MovieLargeAdapter
    private val movieDetailsViewModel: MovieDetailsViewModel by activityViewModels()
    private val similarViewModel: SimilarViewModel by viewModels()

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSimilarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners() {
        initRecyclerView()
        initObserver()
    }

    private fun initObserver() {
        movieDetailsViewModel.movieId.observe(viewLifecycleOwner) { movieId ->
            Log.i("INFOTESTE", "initObserver: $movieId")
            getSimilar(movieId)
        }
    }

    private fun getSimilar(movieId: Int) {
        similarViewModel.getSimilar(movieId).observe(viewLifecycleOwner) { stateView ->
            when(stateView) {
                is StateView.Loading -> {

                }
                is StateView.Success -> {
                    Log.i("INFOTESTE", "getSimilar: ${stateView.data.toString()}")
                    movieLargeAdapter.submitList(stateView.data)
                }
                is StateView.Error -> {

                }
            }

        }
    }

    private fun initRecyclerView() {
        movieLargeAdapter = MovieLargeAdapter(
            movieClickListener = { movieId ->
                movieId?.let {
                    val action = MainGraphDirections.actionGlobalMovieDetailsFragment(movieId)
                    findNavController().navigate(action)
                }
            }
        )

        with(binding.rvMovieSimilar) {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = movieLargeAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

}