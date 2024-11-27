package com.example.movieapp.presenter.main.bottomBar.movie_details.tabLayouts.similar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.MainGraphDirections
import com.example.movieapp.databinding.FragmentSimilarBinding
import com.example.movieapp.presenter.main.bottomBar.home.second_screen.moviegenre.adapter.MovieLargeAdapter
import com.example.movieapp.presenter.main.bottomBar.movie_details.MovieDetailsViewModel
import com.example.movieapp.util.StateView
import com.example.movieapp.util.navigateWithAnimations
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
            getSimilar(movieId)
        }
    }

    private fun getSimilar(movieId: Int) {
        similarViewModel.getSimilar(movieId).observe(viewLifecycleOwner) { stateView ->
            when(stateView) {
                is StateView.Loading -> {

                }
                is StateView.Success -> {
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
                    findNavController().navigateWithAnimations(action)
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